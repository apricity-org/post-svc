package com.apricity.post_svc.repository;

import com.apricity.post_svc.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUsername(String username);
}
