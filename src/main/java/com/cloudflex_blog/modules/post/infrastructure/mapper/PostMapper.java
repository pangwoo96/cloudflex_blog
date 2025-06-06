package com.cloudflex_blog.modules.post.infrastructure.mapper;

import com.cloudflex_blog.modules.post.domain.entity.Post;
import com.cloudflex_blog.modules.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    void save(Post post);
}
