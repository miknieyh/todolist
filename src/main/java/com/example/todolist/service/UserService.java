package com.example.todolist.service;

import com.example.todolist.model.UserEntity;
import com.example.todolist.model.UserRepositoty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepositoty repositoty;

    public UserEntity create (final UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null ){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(repositoty.existsByEmail(email)){
            log.warn("Email already exists {}",email);
            throw new RuntimeException("Email already exists");
        }
        return repositoty.save(userEntity);
    }

    public UserEntity getByCredentials( final String email,final String password){
        return repositoty.findByEmailAAndPassword(email,password);
    }
}
