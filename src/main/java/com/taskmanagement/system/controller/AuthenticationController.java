package com.taskmanagement.system.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanagement.system.dto.custom.LoginRequest;
import com.taskmanagement.system.dto.custom.LoginResponse;
import com.taskmanagement.system.service.main.AuthenticationService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletRequest request){
        return authenticationService.login(loginRequest, request);
    }

}
















