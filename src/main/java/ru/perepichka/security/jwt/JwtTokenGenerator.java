package ru.perepichka.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {
    @Value("${secret-key}")
    private String secretKey;

    @Value("${access-token-expiration-days}")
    private int accessTokenExpirationDays;

    @Value("${refresh-token-expiration-weeks}")
    private int refreshTokenExpirationWeeks;

    public String generateAccessToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName()) //claim sub
                .claim("auth", authResult.getAuthorities()) // claim auth
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(accessTokenExpirationDays))) // claim expDate
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())) //signature
                .compact();
    }

    public String generateRefreshToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("auth", authResult.getAuthorities())
                .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(refreshTokenExpirationWeeks)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

}
