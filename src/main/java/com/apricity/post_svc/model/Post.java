package com.apricity.post_svc.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Builder
@Document(collection = "posts")
public class Post {

    @Id
    private String id;
    private String description;
    private String ref;
    private String username;
}
