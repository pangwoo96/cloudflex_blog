package com.cloudflex_blog.modules.post.application.service;

import com.cloudflex_blog.modules.post.application.fileUploader.FileUploader;
import com.cloudflex_blog.modules.post.application.fileUploader.FileUploaderFactory;
import com.cloudflex_blog.modules.post.domain.entity.Post;
import com.cloudflex_blog.modules.post.domain.enums.CSPType;
import com.cloudflex_blog.modules.post.exception.exception.PostException;
import com.cloudflex_blog.modules.post.infrastructure.mapper.PostMapper;
import com.cloudflex_blog.modules.post.web.controller.dto.request.CreatePostReqDto;
import com.cloudflex_blog.modules.post.web.controller.dto.request.UpdatePostReqDto;
import com.cloudflex_blog.modules.post.web.controller.dto.response.CreatePostResDto;
import com.cloudflex_blog.modules.post.web.controller.dto.response.UpdatePostResDto;
import com.cloudflex_blog.modules.user.domain.entity.User;
import com.cloudflex_blog.modules.user.domain.enums.Role;
import com.cloudflex_blog.modules.user.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cloudflex_blog.modules.post.exception.errorcode.PostErrorCode.USER_NOT_CORRECT;

@Service
@RequiredArgsConstructor
public class PostService {

    private final FileUploaderFactory uploaderFactory;
    private final PostMapper postMapper;

    @Transactional
    public CreatePostResDto createPost(CreatePostReqDto reqDto) {

        FileUploader uploader = uploaderFactory.getUploader(reqDto.getCspType());
        String uploadedImageUrl = uploader.upload(reqDto.getImageUrl());

        Post post = Post.of(reqDto.getTitle(), reqDto.getContent(), uploadedImageUrl, reqDto.getCspType(), reqDto.getUserId());
        postMapper.save(post);

        return CreatePostResDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .cspType(String.valueOf(post.getCspType()))
                .userId(post.getUserId())
                .build();
    }

    public UpdatePostResDto updatePost(UpdatePostReqDto reqDto) {
        Post post = postMapper.findPostByPostId(reqDto.getPostId());

        if (post.getUserId() != reqDto.getUserId()) {
            throw new PostException(USER_NOT_CORRECT);
        }

        if (reqDto.getNewImageUrl() != null) {
            FileUploader uploader = uploaderFactory.getUploader(reqDto.getNewCspType());
            String uploadedImageUrl = uploader.upload(reqDto.getNewImageUrl());
            post.updateImage(uploadedImageUrl, CSPType.valueOf(reqDto.getNewCspType()));
        }

        post.updateTitle(reqDto.getTitle());
        post.updateContent(reqDto.getContent());

        postMapper.updatePost(post);

        return UpdatePostResDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .cspType(String.valueOf(post.getCspType()))
                .userId(post.getUserId())
                .build();
    }
}
