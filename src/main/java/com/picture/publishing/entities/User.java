package com.picture.publishing.entities;

import com.picture.publishing.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private static final long serialVersionUID = 2115639733852135379L;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private UserType userType;

    @OneToMany(targetEntity = Picture.class, mappedBy = "user")
    private List<Picture> pictures;

}
