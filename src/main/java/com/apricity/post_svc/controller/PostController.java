package com.apricity.post_svc.controller;

import com.apricity.post_svc.model.Post;
import com.apricity.post_svc.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> uploadPost(@RequestParam("image") MultipartFile multipartFile,
                                     @RequestParam("description") String description,
                                     @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        return new ResponseEntity<>(postService.uploadPost(multipartFile,username, description), HttpStatus.CREATED) ;
    }
}
