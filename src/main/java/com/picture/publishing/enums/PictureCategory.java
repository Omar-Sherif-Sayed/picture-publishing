package com.picture.publishing.enums;

import lombok.Getter;

@Getter
public enum PictureCategory {

    LIVING_THING(1),
    MACHINE(2),
    NATURE(3);

    private final Integer id;

    PictureCategory(Integer id) {
        this.id = id;
    }

}
