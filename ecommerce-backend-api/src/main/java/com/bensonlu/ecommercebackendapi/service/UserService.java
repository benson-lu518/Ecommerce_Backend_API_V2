package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.dto.UserLoginRequest;
import com.bensonlu.ecommercebackendapi.dto.UserRegisterRequest;
import com.bensonlu.ecommercebackendapi.model.User;

public interface UserService {
    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);

    User login (UserLoginRequest userLoginRequest);
}
