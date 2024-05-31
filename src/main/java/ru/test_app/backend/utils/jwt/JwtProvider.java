package ru.test_app.backend.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
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

@Component
public class JwtProvider {
    private int exp;
    private String secret;
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
                    .claim("roles", user.roles)
                    .compact();
        }

        return Jwts.builder().expiration(accessExpiration).signWith(this.getSignKey()).claim("a", "123").compact();
    }

    public boolean validateToken(String token) throws Exception {
        Jwt<?, ?> x = Jwts.parser().verifyWith(this.getSignKey()).build().parse(token);
        return x.getHeader().isEmpty();
    }


    public Object getPayload(String token) throws Exception {
        return Jwts.parser()
                .verifyWith(this.getSignKey())
                .build()
                .parse(token)
                .getPayload();
    }

    public int getId(String token) {
        try{
            Claims jwt = (Claims) this.getPayload(token);
            return (int) jwt.get("id");
        }catch (Exception ex) {
            System.err.print(ex.getMessage() + "\n");
            return -1;
        }
    }

    public Date getAccessExpiration() {
        return accessExpiration;
    }
}
