package com.cloudflex_blog.modules.user.domain.entity;

import com.cloudflex_blog.modules.post.domain.entity.Post;
import com.cloudflex_blog.modules.user.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Post> posts;

    public static User of (String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .role(Role.USER)
                .build();
    }

}
