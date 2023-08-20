package com.mdtech.whatsupbackendclone.service;

import com.mdtech.whatsupbackendclone.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenProvider {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8))
;
    public String generateToken(Authentication authentication) {

        return Jwts.builder()
                .setIssuer("MD Tech")
                .setIssuedAt(new Date(new Date().getTime()+86400000))
                .claim("email", authentication.getName())
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("email"));
    }

}
