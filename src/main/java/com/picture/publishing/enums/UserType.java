package com.picture.publishing.enums;

import lombok.Getter;

@Getter
public enum UserType {

    ADMIN(1),
    CUSTOMER(2);

    private final Integer id;

    UserType(Integer id) { this.id = id; }

}
