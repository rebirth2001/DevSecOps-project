package com.quizly.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.quizly.jwt.Dto.UserDto;
import com.quizly.jwt.configuration.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;

    private String secretKey;

    public JwtService(JwtProperties jwtProperties){
        this.jwtProperties=jwtProperties;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    private Algorithm getSignInKey() {
        return Algorithm.HMAC512(this.secretKey);
    }
    private String buildToken(
            UserDto user
    ) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .sign(getSignInKey());
    }


    public String generateToken(UserDto user) {
        return buildToken(user);
    }

    public Optional<DecodedJWT> decodeJwt(String token){
        try{
            return Optional.of(JWT.require(getSignInKey())
                    .build()
                    .verify(token));
        }catch (JWTVerificationException e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean isTokenExpired(String token) {
        var expiration = extractExpiration(token);
        return expiration.map(date -> date.before(new Date())).orElse(true);
    }

    private Optional<Date> extractExpiration(String token) {
        var decodedToken = decodeJwt(token);
        return decodedToken.map(Payload::getExpiresAt);
    }

    public String extractUsername(String token){
        var decodedToken = decodeJwt(token);
        return decodedToken.map(Payload::getSubject).orElse(null);
    }
}
