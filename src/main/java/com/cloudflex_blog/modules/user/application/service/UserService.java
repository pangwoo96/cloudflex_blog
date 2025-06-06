package com.cloudflex_blog.modules.user.application.service;

import com.cloudflex_blog.modules.user.domain.entity.User;
import com.cloudflex_blog.modules.user.domain.enums.Role;
import com.cloudflex_blog.modules.user.exception.exception.UserException;
import com.cloudflex_blog.modules.user.infrastructure.jwt.JwtProvider;
import com.cloudflex_blog.modules.user.infrastructure.jwt.JwtUtil;
import com.cloudflex_blog.modules.user.infrastructure.mapper.UserMapper;
import com.cloudflex_blog.modules.user.infrastructure.redis.RedisService;
import com.cloudflex_blog.modules.user.web.dto.request.LoginReqDto;
import com.cloudflex_blog.modules.user.web.dto.request.LogoutReqDto;
import com.cloudflex_blog.modules.user.web.dto.request.SignUpReqDto;
import com.cloudflex_blog.modules.user.web.dto.response.LoginResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode.PASSWORD_NOT_CORRECT;
import static com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Transactional
    public void signUp(SignUpReqDto reqDto) {

        User user = User.of(reqDto.getUsername(), reqDto.getPassword());
        userMapper.save(user);
    }

    @Transactional
    public LoginResDto login(LoginReqDto reqDto) {

        String username = reqDto.getUsername();
        String password = reqDto.getPassword();

        User user = userMapper.findUserByUsername(username);

        if (user == null) {
            throw new UserException(USER_NOT_FOUND);
        }

        if (!user.getPassword().equals(password)) {
            throw new UserException(PASSWORD_NOT_CORRECT);
        }

        String accessToken = jwtProvider.createAccessToken(username, Role.USER);
        String refreshToken = jwtProvider.createRefreshToken(username, Role.USER);

        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logout(LogoutReqDto reqDto) {
        // 1. 토큰 추출
        String accessToken = reqDto.getAccessToken();
        String refreshToken = reqDto.getRefreshToken();

        // 2. Access Token과 Refresh Token의 남은 만료시간을 추출
        long remainTimeAccessToken = jwtUtil.getRemainingExpirationMillis(accessToken);
        long remainTimeRefreshToken = jwtUtil.getRemainingExpirationMillis(refreshToken);

        // 3. RedisService에서 Redis에 블랙리스트로 저장
        redisService.saveBlacklist(accessToken, remainTimeAccessToken, refreshToken, remainTimeRefreshToken);
    }

    public LoginResDto refreshToken(String refreshToken) {

        // 1. RefreshToken 검증
        jwtUtil.validateToken(refreshToken);

        // 2. RefreshToken이 블랙리스트인지 확인
        jwtUtil.validateRefreshTokenBlackList(refreshToken);

        // 3. user 조회
        String username = jwtUtil.getUsername(refreshToken);

        User user = userMapper.findUserByUsername(username);

        if (user == null) {
            throw new UserException(USER_NOT_FOUND);
        }

        // 5. 사용된 리프레시 토큰을 블랙리스트로 저장
        redisService.saveRefreshBlackList(refreshToken, jwtUtil.getRemainingExpirationMillis(refreshToken));

        // 6. Token에 사용자 정보를 담기
        String accessToken = jwtProvider.createAccessToken(user.getUsername(), Role.USER);
        String newRefreshToken = jwtProvider.createRefreshToken(user.getUsername(), Role.USER);

        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();

    }

}
