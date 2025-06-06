package com.cloudflex_blog.modules.post.web.controller.dto.response;

import com.cloudflex_blog.modules.post.domain.enums.CSPType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResDto {

    private String title;

    private String content;

    private String imageUrl;

    private String cspType;

    private Long userId;
}
