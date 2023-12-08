package com.taskmanagement.system.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {


    @Value("${jwt.security.key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;


    public String generateToken(String uuid){
        return Jwts
                .builder()
                .setSubject(uuid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenNonExpired(String token){
        Date expirationTime = extractClaim(token, Claims::getExpiration);
        return expirationTime.after(new Date(System.currentTimeMillis()));
    }


    public String extractSubject(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolve){
        Claims claims = extractAllClaims(token);
        return resolve.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}












