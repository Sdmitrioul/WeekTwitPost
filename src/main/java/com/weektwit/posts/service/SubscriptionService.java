package com.weektwit.posts.service;

import com.weektwit.posts.entity.Subscription;
import com.weektwit.posts.repository.SubscriptionRepository;
import com.weektwit.posts.type.JwtUserDetails;
import com.weektwit.posts.type.SubscriptionWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;

    public boolean addSubscription(SubscriptionWrapper request) {
        subscriptionRepository.save(Subscription.builder()
                .subscriberId(getUserId())
                .authorId(request.getAuthorId())
                .build());
        return true;
    }

    public Set<Long> getUserSubscriptions(Long userId) {
        return subscriptionRepository.getAllUserSubscription(userId);
    }

    private Long getUserId() {
        return ((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
