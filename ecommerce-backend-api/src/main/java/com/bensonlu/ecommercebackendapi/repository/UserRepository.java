package com.bensonlu.ecommercebackendapi.repository;

import com.bensonlu.ecommercebackendapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}


