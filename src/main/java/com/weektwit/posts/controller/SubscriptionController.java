package com.weektwit.posts.controller;

import com.weektwit.posts.service.PostService;
import com.weektwit.posts.service.SubscriptionService;
import com.weektwit.posts.type.PostWrapper;
import com.weektwit.posts.type.SubscriptionWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
@Tag(name = "Subscriptions", description = "Api for subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private SubscriptionService service;
    @Operation(
            summary = "Add subscription",
            description = "Add subscription to user feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful addition")
    })
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody SubscriptionWrapper request) {
        return ResponseEntity.ok(service.addSubscription(request));
    }
}
