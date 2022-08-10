package ru.perepichka.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.perepichka.security.UsernamePasswordAuthRequest;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtTokenGenerator jwtTokenGenerator;

    private final AuthenticationManager authenticationManager;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PARAMETER = "Bearer ";
    public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";

    public JwtUsernamePasswordAuthFilter(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator) {
        super("/api/auth");
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthRequest authRequest = parseToUsernamePasswordRequest(request);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        return authenticationManager.authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        String accessToken = jwtTokenGenerator.generateAccessToken(authResult);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(authResult);

        response.addHeader(AUTHORIZATION_HEADER, BEARER_PARAMETER + accessToken);
        response.addHeader(REFRESH_TOKEN_HEADER, BEARER_PARAMETER + refreshToken);
    }

    private UsernamePasswordAuthRequest parseToUsernamePasswordRequest(HttpServletRequest request) {
        try {
            return new ObjectMapper()
                    .readValue(request.getInputStream(), UsernamePasswordAuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
