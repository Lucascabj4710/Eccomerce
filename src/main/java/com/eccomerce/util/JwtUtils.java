package com.eccomerce.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String secretKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String username = authentication.getPrincipal().toString();

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withClaim("roles", roles)
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public DecodedJWT decodeJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            return jwtVerifier.verify(token);

        } catch (JWTVerificationException verificationException) {
            throw new JWTVerificationException("Token invalido");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){

        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String nameClaim){

        return decodedJWT.getClaim(nameClaim);
    }

}
