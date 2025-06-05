package com.cloudflex_blog.modules.user.infrastructure.mapper;

import com.cloudflex_blog.modules.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void save(User user);

    User findUserByUsername(String username);
}
