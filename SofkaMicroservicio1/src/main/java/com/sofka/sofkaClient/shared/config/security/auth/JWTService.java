package com.sofka.sofkaClient.shared.config.security.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class JWTService {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey; // por si lo necesitas internamente
    private final long expirationMinutes;
    private final String issuer;
    private final String keyId;

    public JWTService(KeyPair keyPair,
                      @Value("${security.jwt.expirationMinutes}") long expirationMinutes,
                      @Value("${security.jwt.issuer}") String issuer,
                      @Value("${security.jwt.key-id}") String keyId)
    {
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.expirationMinutes = expirationMinutes;
        this.issuer = issuer;
        this.keyId = keyId;
    }

    public String generateToken(String username, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }


    public Jws<Claims> decodeToken(String authToken) throws JwtException {
        String token = authToken.replace("Bearer ", "");
        return parseToken(token);
    }

    public Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey) // valida localmente
                .requireIssuer(issuer)    // asegura iss
                .build()
                .parseClaimsJws(token);
    }
}
