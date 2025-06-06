package com.cloudflex_blog.modules.post.web.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostReqDto {

    private Long postId;

    private String title;

    private String content;

    private String newImageUrl;

    private String newCspType;

    private Long userId;
}
