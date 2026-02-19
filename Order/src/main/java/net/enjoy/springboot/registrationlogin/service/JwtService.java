package net.enjoy.springboot.registrationlogin.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.enjoy.springboot.registrationlogin.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET_KEY =
            "your-256-bit-secret-key-your-256-bit-secret-key";

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    public boolean isTokenValid(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .after(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

