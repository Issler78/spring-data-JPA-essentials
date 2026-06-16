package br.com.issler.spring_boot_essentials.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
public class TokenProvider {
    @Value("${spring.jwt.expiration}")
    private long expirationTime;

    @Value("${spring.jwt.key}")
    private String secret;

    // gerar token
    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buildToken(Objects.requireNonNull(user).getUsername());
    }

    private String buildToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }




    // validate token
    public boolean isTokenValid(String token) {
        try {
            getClaimsFromToken(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }



    // extrair informações do token
    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }



    // get payload of token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
