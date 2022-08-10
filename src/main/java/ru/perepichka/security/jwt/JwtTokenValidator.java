package ru.perepichka.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenValidator {
    @Value("${secret-key}")
    private String secretKey;

    UserDetailsService userDetailsService;
    public static final String BEARER_PARAMETER = "Bearer ";


    public Authentication extractAuthentication(String authorizationHeader) {
        validateHeader(authorizationHeader);
        try {
            Claims claims = extractClaims(authorizationHeader);
            String username = getUsername(claims);
            Set<SimpleGrantedAuthority> authorities = getAuthorities(claims);
            return new UsernamePasswordAuthenticationToken(
                    userDetailsService.loadUserByUsername(username),
                    null,
                    authorities
            );
        } catch (JwtException e) {
            throw new MalformedJwtException(String.format("Can not authorize token: %s", authorizationHeader));
        }

    }

    private void validateHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith(BEARER_PARAMETER)) {
            throw new MalformedJwtException(String.format("Invalid token format: %s", authorizationHeader));
        }
    }

    private String getUsername(Claims claims) {
        return claims.getSubject();
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        var auth = (List<Map<String, String>>) claims.get("auth");

        return auth.stream()
                .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toSet());

    }

    public Claims extractClaims(String authorizationHeader) {
        validateHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();
        jwtParserBuilder.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()));
        Jws<Claims> jws = jwtParserBuilder.build().parseClaimsJws(token);
        Claims claims = jws.getBody();

        if (claims
                .getExpiration()
                .before(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
        ) {
            throw new ExpiredJwtException(jws.getHeader(), jws.getBody(), "JWT token is expired");
        }
        return claims;
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.replace(BEARER_PARAMETER, "");
    }
}
