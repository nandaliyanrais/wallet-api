package com.assignment.walletapi.authentication.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.assignment.walletapi.authentication.models.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${simple.app.jwt.secret}")
    private String jwtSecret;

    @Value("${simple.app.jwt.expiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // tempat untuk menyimpan data pada jwt
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("userId", userPrincipal.getId());
        userInformation.put("username", userPrincipal.getUsername());
        userInformation.put("name", userPrincipal.getName());
        userInformation.put("email", userPrincipal.getEmail());

        // generated jwt
        return Jwts.builder().setClaims(userInformation).setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (jwtExpiration * 1000)))
                .signWith(this.generateKey(), SignatureAlgorithm.HS256).compact();
    }

    String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(token).getBody()
                .getSubject();
    }

    boolean isJwtTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException exception) {
            logger.error("Invalid JWT signature -> Message: {0} ", exception);
        } catch (MalformedJwtException exception) {
            logger.error("Invalid JWT token -> Message: {0}", exception);
        } catch (ExpiredJwtException exception) {
            logger.info("Expired JWT token -> Message: {0}", exception);
        } catch (UnsupportedJwtException exception) {
            logger.error("Unsupported JWT token -> Message: {0}", exception);
        } catch (IllegalArgumentException exception) {
            logger.error("JWT claims string is empty -> Message: {0}", exception);
        }

        return false;
    }

    private Key generateKey() {
        byte[] bytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }
}
