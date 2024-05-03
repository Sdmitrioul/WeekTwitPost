package com.weektwit.posts.repository;

import com.weektwit.posts.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select distinct s.authorId from Subscription s where s.subscriberId = :userId")
    Set<Long> getAllUserSubscription(@Param("userId") Long userId);
}
