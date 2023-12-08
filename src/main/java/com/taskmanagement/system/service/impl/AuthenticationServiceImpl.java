package com.taskmanagement.system.service.impl;

import com.taskmanagement.system.dto.custom.LoginRequest;
import com.taskmanagement.system.dto.custom.LoginResponse;
import com.taskmanagement.system.entity.User;
import com.taskmanagement.system.redis.UserSession;
import com.taskmanagement.system.redis.UserSessionRedisRepository;
import com.taskmanagement.system.repository.UserRepository;
import com.taskmanagement.system.security.jwt.JwtUtil;
import com.taskmanagement.system.security.role.Role;
import com.taskmanagement.system.service.main.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.taskmanagement.system.helper.ResponseEntityHelper.ERROR_RESPONSE;
import static com.taskmanagement.system.helper.ResponseMessageHelper.common_error_message;
import static com.taskmanagement.system.helper.ResponseMessageHelper.error_while_operation;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRedisRepository redisRepository;
    private final JwtUtil jwtUtil;


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findUserByEmail(loginRequest.getUsername());

        if(userOptional.isEmpty())
            return ERROR_RESPONSE(
                    common_error_message("Username is incorrect!"),
                    403
            );

        if(!passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword()))
            return ERROR_RESPONSE(
                    common_error_message("Password is incorrect!"),
                    403
            );

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userOptional.get(),
                        null,
                        userOptional.get().getAuthorities()
                );

        authenticationToken.setDetails(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        String uuid = UUID.randomUUID().toString();

        UserSession userSession = UserSession.builder()
                .id(uuid).value(userOptional.get()).build();

        try {
            redisRepository.save(userSession);
        }catch (Exception e){
            logger.error("Error while logging in: ".concat(e.getMessage()));
            return ERROR_RESPONSE(
                    error_while_operation("user", "logging in"),
                    500
            );
        }

        String jwtToken = jwtUtil.generateToken(uuid);

        LoginResponse response = new LoginResponse();
        Role role = userOptional.get().getRole();
        response.setRole(role.name());
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }


}


