package com.taskmanagement.system.security.jwt;


import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.taskmanagement.system.redis.UserSession;
import com.taskmanagement.system.redis.UserSessionRedisRepository;
import com.taskmanagement.system.entity.User;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;
    private final UserSessionRedisRepository redisRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String token;
        final String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            try {
                token = authHeader.substring(7);
                boolean nonExpired = jwtUtil.isTokenNonExpired(token);
                if(!nonExpired)
                    throw new RuntimeException("Token has expired or invalid");

                String uuid = jwtUtil.extractSubject(token);

                Optional<UserSession> optionalUser = redisRepository.findById(uuid);
                if (optionalUser.isEmpty()) {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    throw new RuntimeException("Token has expired or invalid");
                }
                User user = optionalUser.get().getValue();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                token,
                                user.getAuthorities()
                        );
                authenticationToken.setDetails(request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (RuntimeException e){
                logger.warn("Invalid token with error: {}", e.getMessage());
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request, response);
    }


}

















