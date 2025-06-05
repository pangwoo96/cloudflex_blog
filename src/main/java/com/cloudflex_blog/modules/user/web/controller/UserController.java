package com.cloudflex_blog.modules.user.web.controller;

import com.cloudflex_blog.modules.user.application.service.UserService;
import com.cloudflex_blog.modules.user.web.dto.request.LoginReqDto;
import com.cloudflex_blog.modules.user.web.dto.request.LogoutReqDto;
import com.cloudflex_blog.modules.user.web.dto.request.SignUpReqDto;
import com.cloudflex_blog.modules.user.web.dto.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/")
    public ResponseEntity<Void> signUp(SignUpReqDto reqDto) {
        userService.signUp(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginReqDto reqDto) {
        LoginResponseDto resDto = userService.login(reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(LogoutReqDto reqDto) {
        userService.logout(reqDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
