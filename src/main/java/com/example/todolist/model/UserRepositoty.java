package com.example.todolist.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoty extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByEmailAAndPassword(String email, String password);
}