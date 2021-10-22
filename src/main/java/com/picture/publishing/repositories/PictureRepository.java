package com.picture.publishing.repositories;

import com.picture.publishing.entities.Picture;
import com.picture.publishing.entities.User;
import com.picture.publishing.enums.PictureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Integer countByUser(User user);

    List<Picture> findAllByDeletedFalseAndPictureStatus(PictureStatus pictureStatus);

}
