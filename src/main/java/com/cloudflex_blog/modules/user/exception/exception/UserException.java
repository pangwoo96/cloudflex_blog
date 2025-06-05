package com.cloudflex_blog.modules.user.exception.exception;

import com.cloudflex_blog.modules.user.exception.errorcode.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
