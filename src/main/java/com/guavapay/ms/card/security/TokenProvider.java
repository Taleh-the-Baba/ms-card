package com.guavapay.ms.card.security;

import com.guavapay.ms.card.error.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    @Value("${application.security.authentication.jwt.secret}")
    private String secret;

    @Value("${application.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication parseAuthentication(String token) {
        Claims claims = getClaims(token);
        UserPrincipal principal = getPrincipal(claims);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private UserPrincipal getPrincipal(Claims claims) {
        String username = claims.get(TokenKey.USERNAME, String.class);
        Long id = claims.get(TokenKey.USER_ID, Long.class);
        return new UserPrincipal(id, username);
    }

    public Long getUserId() {
        String token = SecurityContextUtils.getCurrentUserJWT();
        return getClaims(token).get(TokenKey.USER_ID, Long.class);
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Fail on parsing token: {}", token);
            throw new InvalidTokenException();
        }
    }

    private String createToken(CustomUserPrincipal principal) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInSeconds * 1000);

        return Jwts.builder()
                .claim(TokenKey.TOKEN_TYPE, TokenType.ACCESS)
                .claim(TokenKey.USER_ID, principal.getId())
                .claim(TokenKey.USERNAME, principal.getUsername())
                .claim(TokenKey.FULL_NAME, principal.getFullName())
                .claim(TokenKey.EMAIL, principal.getEmail())
                .claim(TokenKey.PHONE, principal.getMobile())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public JwtToken createJwtToken(CustomUserPrincipal principal) {
        return JwtToken.builder()
                .accessToken(createToken(principal))
                .build();
    }
}
