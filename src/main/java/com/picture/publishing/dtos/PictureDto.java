package com.picture.publishing.dtos;

import com.picture.publishing.entities.Picture;
import com.picture.publishing.enums.PictureCategory;
import com.picture.publishing.enums.PictureStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PictureDto {

    private Long id;
    private Date createdDate;
    private String name;
    private String description;
    private String url;
    private Integer width;
    private Integer height;
    private Long size;
    private PictureCategory pictureCategory;
    private PictureStatus pictureStatus;
    private boolean deleted;

    public PictureDto convertEntityToDto(Picture picture) {
        id = picture.getId();
        createdDate = picture.getCreatedDate();
        name = picture.getName();
        description = picture.getDescription();
        url = picture.getUrl();
        width = picture.getWidth();
        height = picture.getHeight();
        size = picture.getSize();
        pictureCategory = picture.getPictureCategory();
        pictureStatus = picture.getPictureStatus();
        deleted = picture.isDeleted();
        return this;
    }


}
