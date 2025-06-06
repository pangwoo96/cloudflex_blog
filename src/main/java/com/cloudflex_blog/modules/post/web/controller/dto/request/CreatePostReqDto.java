package com.cloudflex_blog.modules.post.web.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostReqDto {

    private String title;

    private String content;

    private String imageUrl;

    private String cspType;

    private Long userId;
}
