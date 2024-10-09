package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.model.User;
import com.bensonlu.ecommercebackendapi.repository.OrderItemRepository;
import com.bensonlu.ecommercebackendapi.repository.UserRepository;
import com.bensonlu.ecommercebackendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User register(User user) {
        // Check if the email has been used
        User checkedUser = userRepository.findByEmail(user.getEmail());
        if (checkedUser != null) {
            log.warn("The email {} has been used", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        // Hash the password
        String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

        // Set to the new user with hashed password
        User hashedUser = new User();
        hashedUser.setEmail(user.getEmail());
        hashedUser.setPassword(hashedPassword);
        hashedUser.setCreatedDate(new java.util.Date());
        hashedUser.setLastModifiedDate(new java.util.Date());

        // Save the user to DB
        User registeredUser = userRepository.save(hashedUser);

        return registeredUser;
    }

    @Override
    public User login(User user) {
        // Find user by email using JPA
        User checkedUser = userRepository.findByEmail(user.getEmail());

        // Check if the user exists by email
        if (checkedUser == null) {
            log.warn("The email {} has not been registered", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not registered");
        }

        // Hash the input password using MD5
        String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

        // Check if the password matches
        if (checkedUser.getPassword().equals(hashedPassword)) {
            return checkedUser;
        } else {
            log.warn("The password for email {} is incorrect", checkedUser.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }
    }

}
