package com.cloudflex_blog.modules.post.domain.entity;

import com.cloudflex_blog.modules.post.domain.enums.CSPType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Post {

    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private CSPType cspType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public static Post of(String title, String content, String imageUrl, String cspType, Long userId) {
        return Post.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .cspType(CSPType.valueOf(cspType))
                .userId(userId)
                .build();
    }

    public void updateImage(String imageUrl, CSPType cspType) {
        this.imageUrl = imageUrl;
        this.cspType = cspType;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
