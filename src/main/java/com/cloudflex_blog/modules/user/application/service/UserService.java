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
import com.cloudflex_blog.modules.user.web.dto.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode.PASSWORD_NOT_CORRECT;
import static com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public void signUp(SignUpReqDto reqDto) {
        String username = reqDto.getUsername();
        String password = reqDto.getPassword();

        User user = User.of(username, password);
        userMapper.save(user);
    }

    public LoginResponseDto login(LoginReqDto reqDto) {

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

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


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


}
