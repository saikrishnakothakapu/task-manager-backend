package com.taskmanager.service;

import com.taskmanager.dto.AuthResponse;
import com.taskmanager.dto.LoginRequest;
import com.taskmanager.dto.RegisterRequest;
import com.taskmanager.exception.UserException;

public interface UserService {

    String registerUser(RegisterRequest registerRequest) throws UserException;

    AuthResponse loginUser(LoginRequest loginRequest) throws UserException;


}
