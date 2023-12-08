package com.taskmanagement.system.service.main;

import com.taskmanagement.system.dto.custom.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request);
}
