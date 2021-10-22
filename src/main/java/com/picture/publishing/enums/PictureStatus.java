package com.picture.publishing.enums;

import lombok.Getter;

@Getter
public enum PictureStatus {

    UNPROCESSED(1),
    ACCEPTED(2),
    REJECTED(3);

    private final Integer id;

    PictureStatus(Integer id) {
        this.id = id;
    }

}
