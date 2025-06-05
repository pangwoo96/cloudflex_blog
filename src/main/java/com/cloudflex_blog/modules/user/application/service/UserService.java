package com.cloudflex_blog.modules.user.application.service;

import com.cloudflex_blog.modules.user.domain.entity.User;
import com.cloudflex_blog.modules.user.domain.enums.Role;
import com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode;
import com.cloudflex_blog.modules.user.exception.exception.UserException;
import com.cloudflex_blog.modules.user.infrastructure.jwt.JwtProvider;
import com.cloudflex_blog.modules.user.infrastructure.mapper.UserMapper;
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

    public void signUp(SignUpReqDto reqDto) {
        String username = reqDto.getUsername();
        String password = reqDto.getPassword();

        User user = User.of(username, password);
        userMapper.save(user);
    }

    public LoginResponseDto login(SignUpReqDto reqDto) {

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


}
