package com.cloudflex_blog.modules.user.infrastructure.jwt;

import com.cloudflex_blog.modules.user.domain.enums.Role;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component // 빈 등록
public class JwtProvider {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    private static final String USERNAME = "username";
    private static final String ROLE = "role";

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
    public String createToken(String username, Long expiredMs, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(username))
                .claim(ROLE, role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Access Token 생성 로직
     * @param username
     * @return AccessToken
     */
    public String createAccessToken(String username, Role role) {
        return createToken(username, accessTokenExpiration, role.name());
    }

    /**
     * Refresh Token 생성 로직
     * @param username
     * @return Refresh Token
     */
    public String createRefreshToken(String username, Role role) {
        return createToken(username, refreshTokenExpiration, role.name());
    }

}
