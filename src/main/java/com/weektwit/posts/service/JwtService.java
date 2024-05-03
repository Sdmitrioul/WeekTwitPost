package com.weektwit.posts.service;

import com.weektwit.posts.type.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Clock;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final Clock clock;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public JwtService(Clock clock) {
        this.clock = clock;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSecretKey() {
        return Decoders.BASE64.decode(secretKey);
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(getSecretKey());
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        return extractUsername(jwt).equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(Date.from(clock.instant()));
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(Date.from(clock.instant()))) {
                return false;
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new UsernameNotFoundException("Invalid JWT");
        }
        return true;
    }

    public Authentication authenticate(String jwt) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
        UserDetails userDetails = JwtUserDetails.builder()
                .id(claims.getBody().get("userId", Long.class))
                .email(claims.getBody().getSubject())
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
