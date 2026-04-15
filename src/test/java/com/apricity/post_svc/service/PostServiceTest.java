package com.apricity.post_svc.service;

import com.apricity.post_svc.model.Post;
import com.apricity.post_svc.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private PostService postService;

    private static final String imageUrl = "abc";
    private static final String description = "xyz";
    private static final String username = "mike";

    @Test
    @DisplayName("Should successfully upload post and return saved details")
    void uploadPostSuccess(){

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Post savedPost = Post.builder()
                .id(UUID.randomUUID().toString())
                .description(description)
                .username(username)
                .ref(imageUrl)
                .build();

        when(fileUploadService.uploadFile(multipartFile)).thenReturn(imageUrl);
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        Post result = postService.uploadPost(multipartFile, username, description);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getRef()).isNotNull();
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getRef()).isEqualTo(imageUrl);

        // to check if the service is passing the right parameters to the post it's saving
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getDescription()).isEqualTo(description);
        assertThat(capturedPost.getUsername()).isEqualTo(username);
        assertThat(capturedPost.getRef()).isEqualTo(imageUrl);

        // verify the mocked dependencies are only called once
        verify(fileUploadService).uploadFile(multipartFile);
        verifyNoMoreInteractions(fileUploadService, postRepository);
    }
}
