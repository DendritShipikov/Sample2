package com.toto.sample2.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Date;
import java.security.SignatureException;
import java.lang.IllegalArgumentException;

@Service
@PropertySource("classpath:jwt.properties")
public class JwtService {

    static private final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    @Value("${sample2.secret}")
    private String secret;

    public String generateToken(String username) {
        Date now  = new Date();
        Date expiration = new Date(now.getTime() + 1000000);
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            LOGGER.error("JWT token validation error");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
    
}