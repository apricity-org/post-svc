package com.apricity.post_svc.service;

import com.apricity.post_svc.cloudinary.FileUploadService;
import com.apricity.post_svc.model.Post;
import com.apricity.post_svc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileUploadService fileUploadService;

    public Post uploadPost(MultipartFile multipartFile, String username, String description) {

        String ref = fileUploadService.uploadFile(multipartFile);

        Post uploadedPost = Post.builder()
                .description(description)
                .username(username)
                .ref(ref)
                .build();

        return postRepository.save(uploadedPost);
    }
}
