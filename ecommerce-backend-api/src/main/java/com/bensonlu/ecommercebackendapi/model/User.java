package com.bensonlu.ecommercebackendapi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Entity
@Table(name = "user") // Maps this class to the 'user' table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate user ID
    @Column(name = "user_id") // Map to 'user_id' column in the database
    private Integer userId;

    @Column(name = "email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensures password is sent in request but not exposed in responses
    @Column(name = "password")
    private String password;

    @Column(name = "created_date", updatable = false) // Prevent updates after creation
    private Date createdDate;

    @Column(name = "last_modified_date") // Allow updates
    private Date lastModifiedDate;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
