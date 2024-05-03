package com.weektwit.posts.service;

import com.weektwit.posts.entity.Post;
import com.weektwit.posts.repository.PostRepository;
import com.weektwit.posts.type.JwtUserDetails;
import com.weektwit.posts.type.PostWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final PostRepository repository;
    private final SubscriptionService subscriptionService;
    private final Clock clock;

    public PostWrapper addPost(PostWrapper request) {
        var userId = getUserId();
        var post = map(request);
        post.setId(userId);
        post.setTime(Date.from(clock.instant()));
        post = repository.save(post);
        return map(post);
    }

    public List<Post> getPosts() {
        Set<Long> subscriptions = subscriptionService.getUserSubscriptions(getUserId());
        return repository.findByAuthors(subscriptions);
    }


    private Post map(PostWrapper post) {
        return Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .build();
    }

    private PostWrapper map(Post post) {
        return PostWrapper.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .time(dateFormat.format(post.getTime()))
                .build();
    }

    private Long getUserId() {
        return ((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public List<PostWrapper> getFeed() {
        return getPosts().stream().map(this::map).toList();
    }
}
