package com.cloudflex_blog.modules.user.web.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;

    private String refreshToken;
}
