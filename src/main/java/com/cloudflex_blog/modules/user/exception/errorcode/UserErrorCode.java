package com.cloudflex_blog.modules.user.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode{

    ACCESS_TOKEN_EXPIRED("엑세스 토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("리프레시 토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_CORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
