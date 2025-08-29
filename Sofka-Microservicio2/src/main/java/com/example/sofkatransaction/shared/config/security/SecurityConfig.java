package com.example.sofkatransaction.shared.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain security(HttpSecurity http,
                                        JwtDecoder jwtDecoder,
                                        JwtAuthenticationConverter jwtAuthConverter,
                                        AuthenticationEntryPoint authEntryPoint,
                                        AccessDeniedHandler accessDeniedHandler,
                                        CorsConfigurationSource cors) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(c -> c.configurationSource(cors))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        )
                )
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}") String issuer) {

        return NimbusJwtDecoder
                .withJwkSetUri(issuer)
                .jwsAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopeConv = new JwtGrantedAuthoritiesConverter();
        scopeConv.setAuthorityPrefix("SCOPE_");
        scopeConv.setAuthoritiesClaimName("scope"); // string con espacios p. ej. "accounts:read transact:write"

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> out = new HashSet<>(scopeConv.convert(jwt));

            List<String> scp = jwt.getClaimAsStringList("scp");
            if (scp != null) {
                out.addAll(scp.stream().map(s -> new SimpleGrantedAuthority("SCOPE_" + s)).collect(Collectors.toSet()));
            }
            List<String> auths = jwt.getClaimAsStringList("authorities");
            if (auths != null) {
                out.addAll(auths.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
            }
            return out;
        });
        return authConverter;
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (HttpServletRequest req, HttpServletResponse res, org.springframework.security.core.AuthenticationException ex) -> {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"unauthorized\",\"message\":\"" + safe(ex.getMessage()) + "\"}");
        };
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (HttpServletRequest req, HttpServletResponse res, org.springframework.security.access.AccessDeniedException ex) -> {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"forbidden\",\"message\":\"" + safe(ex.getMessage()) + "\"}");
        };
    }

    private String safe(String s) {
        return (s == null) ? "" : s.replace("\"", "'");
    }

}