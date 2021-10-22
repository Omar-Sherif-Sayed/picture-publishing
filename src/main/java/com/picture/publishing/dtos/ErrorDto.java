package com.picture.publishing.dtos;

import com.picture.publishing.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 4707915955975296184L;

    private Integer id;
    private String message;

    public ErrorDto(ErrorCode error) {
        this.id = error.getId();
        this.message = error.getMessage();
    }

}
