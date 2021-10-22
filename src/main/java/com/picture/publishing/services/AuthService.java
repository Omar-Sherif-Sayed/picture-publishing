package com.picture.publishing.services;

import com.picture.publishing.dtos.ErrorDto;
import com.picture.publishing.dtos.UserDto;
import com.picture.publishing.entities.User;
import com.picture.publishing.enums.ErrorCode;
import com.picture.publishing.enums.UserType;
import com.picture.publishing.repositories.UserRepository;
import com.picture.publishing.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {

        var errors = new ArrayList<ErrorDto>();
        validateUserDto(userDto, errors);

        if (!errors.isEmpty())
            return new ResponseEntity<>(errors.get(0), HttpStatus.NOT_ACCEPTABLE);

        var findUserWithEmail = userRepository.findByEmail(userDto.getEmail());
        if (findUserWithEmail != null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.EXIST_EMAIL), HttpStatus.NOT_ACCEPTABLE);

        var findUserWithUsername = userRepository.findByUsername(userDto.getUsername());
        if (findUserWithUsername != null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.EXIST_USERNAME), HttpStatus.NOT_ACCEPTABLE);
        
        var user = new User();
        user.setCreatedDate(DateUtil.getCurrentDate());
        user.setUserType(UserType.CUSTOMER);
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateUserDto(UserDto userDto, List<ErrorDto> errors) {
        if (userDto.getUsername() == null || userDto.getUsername().trim().equals(""))
            errors.add(new ErrorDto(ErrorCode.REQUIRED_USERNAME));
        if (userDto.getEmail() == null || userDto.getEmail().trim().equals(""))
            errors.add(new ErrorDto(ErrorCode.REQUIRED_EMAIL));
        if (userDto.getPassword() == null || userDto.getPassword().trim().equals(""))
            errors.add(new ErrorDto(ErrorCode.REQUIRED_PASSWORD));
    }

}
