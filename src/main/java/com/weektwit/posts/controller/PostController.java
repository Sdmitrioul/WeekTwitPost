package com.weektwit.posts.controller;


import com.weektwit.posts.service.PostService;
import com.weektwit.posts.type.PostWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "Posts", description = "Api for posts")
@RequiredArgsConstructor
public class PostController {
    private PostService service;
    @Operation(
            summary = "Add post",
            description = "Add post to user feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful addition")
    })
    @PostMapping("/add")
    @SendTo("/feed")
    public ResponseEntity<PostWrapper> add(@RequestBody PostWrapper request) {
        return ResponseEntity.ok(service.addPost(request));
    }

    @Operation(
            summary = "Get feed",
            description = "Get user feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful result")
    })
    @GetMapping("/add")
    public ResponseEntity<List<PostWrapper>> feed() {
        return ResponseEntity.ok(service.getFeed());
    }
}
