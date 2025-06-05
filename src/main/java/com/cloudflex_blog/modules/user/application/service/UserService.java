package com.cloudflex_blog.modules.user.application.service;

import com.cloudflex_blog.modules.user.domain.entity.User;
import com.cloudflex_blog.modules.user.infrastructure.mapper.UserMapper;
import com.cloudflex_blog.modules.user.web.dto.SignUpReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void signUp(SignUpReqDto reqDto) {
        String username = reqDto.getUsername();
        String password = reqDto.getPassword();

        User user = User.of(username, password);
        userMapper.save(user);
    }
}
