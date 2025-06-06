package com.cloudflex_blog.modules.post.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PostErrorCode {

    WRONG_CSP_TYPE("올바르지 않은 CSP 타입입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_CORRECT("해당 유저가 작성한 게시글이 아닙니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
