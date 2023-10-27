package com.huynhthehoa.nutzen.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${custom.key}")
    private String secretKey;
    private final long expireTime = 8 * 60 * 60 * 1000;

    public String generateToken(String data) {
        Date now = new Date();
        Date endTime = new Date(now.getTime() + expireTime);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        return Jwts.builder()
                .signWith(key)
                .expiration(endTime)
                .setSubject(data)
                .compact();

    }

    public String validToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        return Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
