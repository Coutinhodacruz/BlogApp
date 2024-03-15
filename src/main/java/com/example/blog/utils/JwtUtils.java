package com.example.blog.utils;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtUtils {


    public static String generateAccessToken(Long id){
        return JWT.create()
                .withClaim("User_id", id)
                .withIssuer("blog_app")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(2592000)))
                .sign(Algorithm.HMAC512("blog_secret"));

    }

    public static String retrieveAndVerifyToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            throw new UnauthorizedException(INVALID_AUTHORIZATION_HEADER_EXCEPTION.getMessage());
        }

        String authorizationToken = authorizationHeader.substring(SEVEN);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .withIssuer(APP_NAME)
                .withClaimPresence(USER_ID)
                .build();

        DecodedJWT verifiedToken = verifier.verify(authorizationToken);

        if (verifiedToken != null) {
            return authorizationToken;
        }
        throw new UnauthorizedException(VERIFICATION_FAILED_EXCEPTION.getMessage());
    }

    public static String extractUserIdFromToken(String token){
        DecodedJWT decodedJWT =JWT.decode(token);
        Map<String, Claim> claimMap = decodedJWT.getClaims();
        if (claimMap.containsKey(USER_ID)){
            return claimMap.get(USER_ID).asString();
        }
        throw new UnauthorizedException(VERIFICATION_FAILED_EXCEPTION.getMessage());
    }
}