package com.picture.publishing.dtos;

import com.picture.publishing.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserDto {

    private Long id;
    private Date createdDate;
    private String username;
    private String email;
    private String password;

    public UserDto convertEntityToDto(User user) {
        id = user.getId();
        createdDate = user.getCreatedDate();
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        return this;
    }

}
