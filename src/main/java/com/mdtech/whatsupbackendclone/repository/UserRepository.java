package com.mdtech.whatsupbackendclone.repository;

import com.mdtech.whatsupbackendclone.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT U FROM User U WHERE U.firstName LIKE %:query% OR U.lastName LIKE %:query% " +
            "OR U.email LIKE %:query% OR contactNo LIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}
