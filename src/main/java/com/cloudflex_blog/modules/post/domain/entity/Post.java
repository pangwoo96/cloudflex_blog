package com.cloudflex_blog.modules.post.domain.entity;

import com.cloudflex_blog.modules.post.domain.enums.CSPType;

import java.time.LocalDateTime;

public class Post {

    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private CSPType cspType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}
