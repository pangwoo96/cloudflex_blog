package com.cloudflex_blog.modules.post.exception.exception;

import com.cloudflex_blog.modules.post.exception.errorcode.PostErrorCode;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException{

    private final PostErrorCode errorCode;

    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
