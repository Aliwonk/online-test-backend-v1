package ru.test_app.backend.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.test_app.backend.controllers.user.database.entity.ROLES;
import ru.test_app.backend.controllers.user.database.entity.RoleEntity;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtProvider {
    private final int exp;
    private final String secret;
    private Date accessExpiration;


    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.exp}") int exp) {
        this.secret = secret;
        this.exp = exp;
    }

    private SecretKey getSignKey() {
        byte[] key = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(Object object) {
        try {
            final LocalDateTime now = LocalDateTime.now();
            final Instant accessInstant = now.plusMinutes(exp).atZone(ZoneId.systemDefault()).toInstant();
            final Date accessExpiration = Date.from(accessInstant);
            this.accessExpiration = accessExpiration;

            if (object instanceof UserEntity user) {
                List<ROLES> roles = new ArrayList<>();

                for (RoleEntity role: user.roles) {
                    roles.add(role.getRole());
                }

                return Jwts.builder()
                        .expiration(accessExpiration)
                        .subject(user.getLastName())
                        .signWith(this.getSignKey())
                        .claim("id", user.getId())
                        .claim("roles", roles)
                        .compact();
            } else {
                return "";
            }
        } catch (Exception ex) {
            System.err.printf("Error jwt provider in method generateToken: %s \n", ex.getMessage());
            return Jwts.builder().expiration(accessExpiration).signWith(this.getSignKey()).claim("a", "123").compact();
        }
    }

    public boolean validateToken(String token) throws Exception {
        Jwt<?, ?> x = Jwts.parser().verifyWith(this.getSignKey()).build().parse(token);
        return x.getHeader().isEmpty();
    }


    public Object getPayload(String token) throws Exception, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
            return Jwts.parser()
                    .verifyWith(this.getSignKey())
                    .build()
                    .parse(token)
                    .getPayload();
    }

    public JwtExceptions checkToken(String token) {
        try {
            Jwts.parser().verifyWith(this.getSignKey()).build().parse(token);
            return JwtExceptions.NON_EXCEPTION;
        } catch (MalformedJwtException malformedException) {
            return JwtExceptions.MALFORMED_JWT_EXCEPTION;
        } catch (ExpiredJwtException expiredException) {
            return JwtExceptions.EXPIRED_JWT_EXCEPTION;
        }
    }

    public UUID getId(String token) {
        try{
            Claims jwt = (Claims) this.getPayload(token);
            return UUID.fromString(jwt.get("id").toString());
        } catch (Exception ex) {
            System.err.printf("Error jwt provider in method getID: %s \n",ex.getMessage() + "\n");
            return null;
        }
    }

    public Date getAccessExpiration() {
        return accessExpiration;
    }
}
