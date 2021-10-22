package com.picture.publishing.repositories;

import com.picture.publishing.entities.User;
import com.picture.publishing.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameOrEmailAndUserType(String username, String email, UserType userType);

}
