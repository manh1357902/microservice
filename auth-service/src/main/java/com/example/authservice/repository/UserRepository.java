package com.example.authservice.repository;

import com.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the found User, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their username and isDeleted=false.
     *
     * @param username the username to search for
     * @return an Optional containing the found User, or empty if not found
     */
    Optional<User> findByUsernameAndIsDeletedFalse(String username);
}
