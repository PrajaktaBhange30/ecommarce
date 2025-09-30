package com.prajakta.ecommerce.security;


import com.prajakta.ecommerce.entity.enums.RoleType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt_expiration}")
    private Long jwtExpiration;

    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, Set<RoleType> roles){
        Map<String, Object> claims = new HashMap<>();
        Set<String> roleNames= roles.stream()
                        .map(RoleType :: name)
                                .collect(Collectors.toSet());
        claims.put("roles", roleNames);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();

    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Handle specifically if needed
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}