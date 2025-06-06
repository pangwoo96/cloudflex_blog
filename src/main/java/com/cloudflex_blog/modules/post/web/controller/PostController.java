package com.cloudflex_blog.modules.post.web.controller;

import com.cloudflex_blog.modules.post.application.service.PostService;
import com.cloudflex_blog.modules.post.web.controller.dto.request.CreatePostReqDto;
import com.cloudflex_blog.modules.post.web.controller.dto.response.CreatePostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<CreatePostResDto> createPost(CreatePostReqDto reqDto) {
        CreatePostResDto resDto = postService.createPost(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }
}
