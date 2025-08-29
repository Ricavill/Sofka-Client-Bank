package com.sofka.sofkaClient.shared.config.security.auth;

import com.sofka.sofkaClient.client.Client;
import com.sofka.sofkaClient.client.ClientDetailService;
import com.sofka.sofkaClient.client.ClientRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final ClientRepository clientRepository;
    private final ClientDetailService clientDetailService;

    public JWTAuthorizationFilter(JWTService jwtService, ClientRepository clientRepository, ClientDetailService clientDetailService) {
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
        this.clientDetailService = clientDetailService;
        System.out.println("JWTAuthorizationFilter initialized");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Jws<Claims> jws = jwtService.decodeToken(authHeader);
            String username = jws.getBody().getSubject();

            Client client = clientRepository.findClientByUsername(username)
                    .orElse(null);
            if (client == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = this.clientDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);


    }


}
