package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.model.User;

public interface UserService {
    User register(User user);
    User login(User user);
}
