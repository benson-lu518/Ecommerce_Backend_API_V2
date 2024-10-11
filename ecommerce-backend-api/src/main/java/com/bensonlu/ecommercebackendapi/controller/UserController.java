package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.entity.User;
import com.bensonlu.ecommercebackendapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        User registeredUser=userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }

    @PostMapping("/users/login")
    public ResponseEntity<User>login(@RequestBody @Valid User user){
        User loginUser=userService.login(user);

        return ResponseEntity.status(HttpStatus.OK).body(loginUser);
    }
}
