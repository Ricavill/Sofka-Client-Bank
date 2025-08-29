package com.sofka.sofkaClient.shared.config.security.auth;

import com.sofka.sofkaClient.shared.commons.ObjectMapperUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenAuthenticationService tokenAuthenticationService;

    public AuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        super(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login"));
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        AuthenticationRequest authenticationRequest = getAuthenticationRequest(request);
        request.setAttribute("authRequest", authenticationRequest);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private AuthenticationRequest getAuthenticationRequest(HttpServletRequest request) throws IOException {
        return ObjectMapperUtils.toObject(request.getInputStream(), AuthenticationRequest.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) request.getAttribute("authRequest");
        String token = this.tokenAuthenticationService.encodeToken(authenticationRequest);
        response.setContentType("application/json");
        response.getWriter().write(ObjectMapperUtils.toString(new JwtResponse(token)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"invalid_credentials\"}");
    }


}
