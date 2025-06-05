package com.cloudflex_blog.modules.user.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutReqDto {

    private String accessToken;

    private String refreshToken;
}
