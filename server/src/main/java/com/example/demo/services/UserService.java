package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.domain.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    String newUser(Model model);

    Boolean logInSuccessful(Model model);

    List<UserEntity> findAll();

}
