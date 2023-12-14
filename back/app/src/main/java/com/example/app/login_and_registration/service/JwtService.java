package com.example.app.login_and_registration.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.example.app.login_and_registration.config.JWTConfig;
import com.example.app.login_and_registration.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService{
    private static final JWTConfig jwtConfig = new JWTConfig();

    private String buildToken(
            @NotNull User user
            /*Map<String, Object> extraClaims*/
    ) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .sign(getSignInKey());
    }

    private Algorithm getSignInKey() {
        return Algorithm.HMAC512(jwtConfig.getSecret().getBytes());
    }

    public String generateToken(User user) {
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

    public String extractEmail(String token){
        var decodedToken = decodeJwt(token);
        return decodedToken.map(Payload::getSubject).orElse(null);
    }

    public String getHeaderName(){
        return jwtConfig.getHeader();
    }

    public String getHeaderPrefix(){
        return jwtConfig.getPrefix();
    }

//    public String extractEmail(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
    //this methode is for extract all claims to use it after for extraction by claim
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
    // this methode is for extract by claim
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

//    public String generateRefreshToken(
//            User user
//    ) {
//        return buildToken(new HashMap<>(), user, refreshExpiration);
//    }

}
