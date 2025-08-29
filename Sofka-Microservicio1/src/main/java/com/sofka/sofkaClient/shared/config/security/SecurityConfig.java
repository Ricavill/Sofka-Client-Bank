package com.sofka.sofkaClient.shared.config.security;

import com.sofka.sofkaClient.client.ClientDetailService;
import com.sofka.sofkaClient.client.ClientRepository;
import com.sofka.sofkaClient.shared.commons.PasswordUtils;
import com.sofka.sofkaClient.shared.config.security.auth.AuthenticationFilter;
import com.sofka.sofkaClient.shared.config.security.auth.JWTAuthorizationFilter;
import com.sofka.sofkaClient.shared.config.security.auth.JWTService;
import com.sofka.sofkaClient.shared.config.security.auth.TokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] POST_URLS = {
            "/clientes"
    };
    private final SecurityProperties securityProperties;
    private final ClientDetailService clientDetailService;

    public SecurityConfig(SecurityProperties securityProperties,
                          ClientDetailService clientDetailService,
                          TokenAuthenticationService tokenAuthenticationService) {
        this.securityProperties = securityProperties;
        this.clientDetailService = clientDetailService;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {

                return PasswordUtils.hash(rawPassword.toString(), securityProperties.getBcryptStrength());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return PasswordUtils.matches(rawPassword.toString(), encodedPassword, securityProperties.getBcryptStrength());
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(clientDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(JWTService jwtService,
                                                         ClientRepository clientRepository,
                                                         ClientDetailService clientDetailService) {
        return new JWTAuthorizationFilter(jwtService, clientRepository, clientDetailService);
    }

    @Bean
    public AuthenticationFilter authenticationFilter(TokenAuthenticationService tokenAuthenticationService,
                                                     AuthenticationManager authenticationManager) {
        AuthenticationFilter f = new AuthenticationFilter(tokenAuthenticationService);
        f.setAuthenticationManager(authenticationManager);
        return f;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JWTAuthorizationFilter jwtAuthorizationFilter,
                                           AuthenticationFilter authenticationFilter) throws Exception {

        http.csrf(csrf -> {
                    try {
                        csrf.disable()
                                .sessionManagement(sm ->
                                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                        .requestMatchers("/login", "/error",
                                                "/.well-known/jwks.json", "/.well-known/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, POST_URLS).permitAll()
                                        .anyRequest().authenticated());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
