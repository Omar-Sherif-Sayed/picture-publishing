package com.picture.publishing.entities;

import com.picture.publishing.enums.PictureCategory;
import com.picture.publishing.enums.PictureStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "picture")
public class Picture extends BaseEntity {

    private static final long serialVersionUID = 5955813907394388491L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "category", nullable = false)
    private PictureCategory pictureCategory;

    @Column(name = "status", nullable = false)
    private PictureStatus pictureStatus;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
