package com.weektwit.posts.repository;

import com.weektwit.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.authorId in (:subscriptions)")
    List<Post> findByAuthors(@Param("subscriptions") Set<Long> subscriptions);
}
