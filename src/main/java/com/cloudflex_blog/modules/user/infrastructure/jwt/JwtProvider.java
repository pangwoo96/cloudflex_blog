package com.cloudflex_blog.modules.user.infrastructure.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


@Component // 빈 등록
public class JwtProvider {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    private static final String USERNAME = "username";
    private static final String NICKNAME_CLAIM = "nickname";
    private static final String EMAIL_CLAIM = "email";
    private static final String NAME_CLAIM = "name";
    private static final String ROLE = "role";
    private static final String TYPE = "type";

    @Value("${JWT_ACCESS_EXPIRATION}")
    private Long accessTokenExpiration;

    @Value("${JWT_REFRESH_EXPIRATION}")
    private Long refreshTokenExpiration;


    public JwtProvider(@Value("${JWT_SECRET_KEY}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    }
