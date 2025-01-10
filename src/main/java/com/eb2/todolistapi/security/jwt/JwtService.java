package com.eb2.todolistapi.security.jwt;

import com.eb2.todolistapi.entities.User;
import com.eb2.todolistapi.security.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyBuilder keyBuilder;
    private final JwtProperties jwtProperties;

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(HashMap<String,Object> extraClaims, User user) {
        return buildToken(extraClaims, user, jwtProperties.getToken());
    }

    public String generateRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, jwtProperties.getRefreshToken());
    }

    private String buildToken(HashMap<String,Object> extraClaims,
                              User user,
                              long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .issuer("TODO-API")
                .header().add("typ","JWT").and()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(expiration).toMillis()))
                .id(UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        KeyPair keyPair = new KeyPair(keyBuilder.loadPublicKey(), keyBuilder.loadPrivateBuilder());
        return keyPair.getPrivate();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);

        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(keyBuilder.loadPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
