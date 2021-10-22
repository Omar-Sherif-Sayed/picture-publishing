package com.picture.publishing.services;

import com.picture.publishing.dtos.ErrorDto;
import com.picture.publishing.dtos.PictureDto;
import com.picture.publishing.entities.Picture;
import com.picture.publishing.enums.ErrorCode;
import com.picture.publishing.enums.PictureCategory;
import com.picture.publishing.enums.PictureStatus;
import com.picture.publishing.enums.UserType;
import com.picture.publishing.repositories.PictureRepository;
import com.picture.publishing.repositories.UserRepository;
import com.picture.publishing.utils.DateUtil;
import com.picture.publishing.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class PictureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String serverContextPath;

    public ResponseEntity<Object> upload(String usernameOrEmail, String password, MultipartFile picture,
                                         String description, PictureCategory pictureCategory) {

        var user = userRepository.findByUsernameOrEmailAndUserType(usernameOrEmail, usernameOrEmail, UserType.CUSTOMER);
        if (user == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_USERNAME_OR_EMAIL), HttpStatus.NOT_ACCEPTABLE);

        if (!user.getPassword().equals(password))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PASSWORD), HttpStatus.NOT_ACCEPTABLE);

        var errors = new ArrayList<ErrorDto>();

        ImageUtil.validateSize(picture, errors);
        if (!errors.isEmpty())
            return new ResponseEntity<>(errors.get(0), HttpStatus.NOT_ACCEPTABLE);

        var fileExtension = ImageUtil.fileExtension(picture, errors);
        if (!errors.isEmpty())
            return new ResponseEntity<>(errors.get(0), HttpStatus.NOT_ACCEPTABLE);

        var dimension = ImageUtil.getImageDimension(picture, errors);

        if (!errors.isEmpty())
            return new ResponseEntity<>(errors.get(0), HttpStatus.NOT_ACCEPTABLE);

        if (dimension == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        long size = picture.getSize();
        var nextPictureCount = pictureRepository.countByUser(user) + 1;

        var filename = user.getId() + "-" + nextPictureCount + "." + fileExtension;
        ImageUtil.upload(filename, picture, errors);

        var url = "http://localhost:" + serverPort + serverContextPath + "/" + ImageUtil.UPLOAD_FOLDER + "/" + filename;

        var pictureEntity = new Picture();
        pictureEntity.setCreatedDate(DateUtil.getCurrentDate());
        pictureEntity.setDeleted(false);
        pictureEntity.setPictureStatus(PictureStatus.UNPROCESSED);
        pictureEntity.setDescription(description);
        pictureEntity.setPictureCategory(pictureCategory);
        pictureEntity.setWidth(dimension.width);
        pictureEntity.setHeight(dimension.height);
        pictureEntity.setSize(size);
        pictureEntity.setName(filename);
        pictureEntity.setUrl(url);
        pictureEntity.setUser(user);
        pictureRepository.save(pictureEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> findAllPicturesInLandingPage() {

        var acceptedPictures = pictureRepository.findAllByDeletedFalseAndPictureStatus(PictureStatus.ACCEPTED);

        var acceptedPicturesDtos = new ArrayList<PictureDto>();

        acceptedPictures.forEach(picture -> acceptedPicturesDtos.add(new PictureDto().convertEntityToDto(picture)));

        return new ResponseEntity<>(acceptedPicturesDtos, HttpStatus.OK);
    }

    public ResponseEntity<Object> findAllUnprocessedPictures(String adminUsernameOrEmail, String adminPassword) {

        var user = userRepository.findByUsernameOrEmailAndUserType(adminUsernameOrEmail, adminUsernameOrEmail, UserType.ADMIN);
        if (user == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_USERNAME_OR_EMAIL), HttpStatus.NOT_ACCEPTABLE);

        if (!user.getPassword().equals(adminPassword))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PASSWORD), HttpStatus.NOT_ACCEPTABLE);

        var unprocessedPictures = pictureRepository.findAllByDeletedFalseAndPictureStatus(PictureStatus.UNPROCESSED);

        var unprocessedPicturesDtos = new ArrayList<PictureDto>();

        unprocessedPictures.forEach(picture -> unprocessedPicturesDtos.add(new PictureDto().convertEntityToDto(picture)));

        return new ResponseEntity<>(unprocessedPicturesDtos, HttpStatus.OK);
    }

    public ResponseEntity<Object> acceptUnprocessedPicture(String adminUsernameOrEmail, String adminPassword, Long pictureId) {

        var user = userRepository.findByUsernameOrEmailAndUserType(adminUsernameOrEmail, adminUsernameOrEmail, UserType.ADMIN);
        if (user == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_USERNAME_OR_EMAIL), HttpStatus.NOT_ACCEPTABLE);

        if (!user.getPassword().equals(adminPassword))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PASSWORD), HttpStatus.NOT_ACCEPTABLE);

        var picture = pictureRepository.findById(pictureId).orElse(null);

        if (picture == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PICTURE_ID), HttpStatus.NOT_ACCEPTABLE);

        if (!picture.getPictureStatus().equals(PictureStatus.UNPROCESSED))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.PICTURE_ALREADY_PROCESSED), HttpStatus.NOT_ACCEPTABLE);

        picture.setPictureStatus(PictureStatus.ACCEPTED);
        pictureRepository.save(picture);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> rejectUnprocessedPicture(String adminUsernameOrEmail, String adminPassword, Long pictureId) {

        var user = userRepository.findByUsernameOrEmailAndUserType(adminUsernameOrEmail, adminUsernameOrEmail, UserType.ADMIN);
        if (user == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_USERNAME_OR_EMAIL), HttpStatus.NOT_ACCEPTABLE);

        if (!user.getPassword().equals(adminPassword))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PASSWORD), HttpStatus.NOT_ACCEPTABLE);

        var picture = pictureRepository.findById(pictureId).orElse(null);

        if (picture == null)
            return new ResponseEntity<>(new ErrorDto(ErrorCode.WRONG_PICTURE_ID), HttpStatus.NOT_ACCEPTABLE);

        if (!picture.getPictureStatus().equals(PictureStatus.UNPROCESSED))
            return new ResponseEntity<>(new ErrorDto(ErrorCode.PICTURE_ALREADY_PROCESSED), HttpStatus.NOT_ACCEPTABLE);

        ImageUtil.delete(picture.getName());

        picture.setPictureStatus(PictureStatus.REJECTED);
        picture.setDeleted(true);
        pictureRepository.save(picture);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
