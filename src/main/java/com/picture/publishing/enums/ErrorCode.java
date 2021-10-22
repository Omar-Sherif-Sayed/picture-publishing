package com.picture.publishing.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    REQUIRED_USERNAME(1, "Username is required"),
    REQUIRED_EMAIL(2, "Email is required"),
    REQUIRED_PASSWORD(3, "Password is required"),
    EXIST_USERNAME(4, "Username already exist"),
    EXIST_EMAIL(5, "Email already exist"),
    WRONG_USERNAME_OR_EMAIL(6, "Wrong username or email"),
    WRONG_PASSWORD(7, "Wrong password"),
    NOT_IMAGE_FILE(8, "Please upload image file"),
    NOT_SUPPORTED_EXTENSION(9, "Please upload image with extensions (jpg, png, gif) only"),
    NOT_SUPPORTED_SIZE(10, "Please upload image with size up to 2 MB"),
    FILE_NOT_HAS_NAME(11, "File must has a name"),
    FILE_NOT_HAS_EXTENSION(12, "File must has a extension"),
    WRONG_PICTURE_ID(13, "Wrong Picture Id"),
    PICTURE_ALREADY_PROCESSED(14, "Picture already processed");

    private final Integer id;
    private final String message;

    ErrorCode(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

}
