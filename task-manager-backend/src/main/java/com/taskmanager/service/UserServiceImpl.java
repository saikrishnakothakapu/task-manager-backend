package com.taskmanager.service;

import com.taskmanager.dto.AuthResponse;
import com.taskmanager.dto.LoginRequest;
import com.taskmanager.dto.RegisterRequest;
import com.taskmanager.entity.User;
import com.taskmanager.entity.enums.Role;
import com.taskmanager.exception.UserException;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(RegisterRequest registerRequest) throws UserException {

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserException("User already exists. Please login");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return "Successfully registered user. Userid: " + savedUser.getId();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) throws UserException {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserException("Invalid email"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserException("Invalid password");
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtUtil.generateToken(loginRequest.getEmail()));


        return authResponse;
    }
}
