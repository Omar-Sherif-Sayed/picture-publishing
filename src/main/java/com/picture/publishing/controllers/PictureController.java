package com.picture.publishing.controllers;

import com.picture.publishing.enums.PictureCategory;
import com.picture.publishing.services.PictureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("pictures")
public class PictureController {

    private final Logger logger = LogManager.getLogger(PictureController.class);

    @Autowired
    private PictureService pictureService;

    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestParam(name = "usernameOrEmail") String usernameOrEmail,
                                         @RequestParam(name = "password") String password,
                                         @RequestParam(name = "picture") MultipartFile picture,
                                         @RequestParam(name = "description") String description,
                                         @RequestParam(name = "pictureCategory") PictureCategory pictureCategory) {
        try {
            return pictureService.upload(usernameOrEmail, password, picture, description, pictureCategory);
        } catch (Exception e) {
            logger.error("Error in PictureController in upload()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("find-all-pictures-in-landing-page")
    public ResponseEntity<Object> findAllPicturesInLandingPage() {
        try {
            return pictureService.findAllPicturesInLandingPage();
        } catch (Exception e) {
            logger.error("Error in PictureController in findAllPicturesInLandingPage()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("find-all-unprocessed-pictures")
    public ResponseEntity<Object> findAllUnprocessedPictures(@RequestParam(name = "adminUsernameOrEmail") String adminUsernameOrEmail,
                                                             @RequestParam(name = "password") String adminPassword) {
        try {
            return pictureService.findAllUnprocessedPictures(adminUsernameOrEmail, adminPassword);
        } catch (Exception e) {
            logger.error("Error in PictureController in findAllUnprocessedPictures()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("accept-unprocessed-picture/{pictureId}")
    public ResponseEntity<Object> acceptUnprocessedPicture(@RequestParam(name = "adminUsernameOrEmail") String adminUsernameOrEmail,
                                                           @RequestParam(name = "password") String adminPassword,
                                                           @PathVariable(name = "pictureId") Long pictureId) {
        try {
            return pictureService.acceptUnprocessedPicture(adminUsernameOrEmail, adminPassword, pictureId);
        } catch (Exception e) {
            logger.error("Error in PictureController in acceptUnprocessedPicture()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("reject-unprocessed-picture/{pictureId}")
    public ResponseEntity<Object> rejectUnprocessedPicture(@RequestParam(name = "adminUsernameOrEmail") String adminUsernameOrEmail,
                                                           @RequestParam(name = "password") String adminPassword,
                                                           @PathVariable(name = "pictureId") Long pictureId) {
        try {
            return pictureService.rejectUnprocessedPicture(adminUsernameOrEmail, adminPassword, pictureId);
        } catch (Exception e) {
            logger.error("Error in PictureController in rejectUnprocessedPicture()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
