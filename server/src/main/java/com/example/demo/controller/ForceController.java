package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForceController {

    UserServiceImpl userServiceImpl;

    @Autowired
    public ForceController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @PostMapping("/loginforce")
    public ResponseEntity<User> loginForce(User user){
        if (userServiceImpl.logInSuccessful(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
    }

}
