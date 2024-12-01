package com.example.databasedesign.controller;

import com.example.databasedesign.common.resolve.UserId;
import com.example.databasedesign.dto.request.CreatePostRequest;
import com.example.databasedesign.dto.response.PostResponse;
import com.example.databasedesign.service.LikeService;
import com.example.databasedesign.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> createPost(@UserId String userId, @RequestBody CreatePostRequest createPostRequest) {
        postService.createPost(userId, createPostRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@UserId String userId, @PathVariable Long postId) {
        likeService.likePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(@RequestParam(required = false) Long lastId) {
        return ResponseEntity.ok(postService.getPosts(lastId));
    }

    @GetMapping("/liked")
    public ResponseEntity<List<PostResponse>> getUserLikedPosts(@UserId String userId, @RequestParam(required = false) Integer pageNo) {
        return ResponseEntity.ok(postService.getUserLikedPosts(pageNo, userId));
    }

    @GetMapping("/like-count")
    public ResponseEntity<List<PostResponse>> getPostsOrderByLikeCount(@RequestParam(required = false) Integer pageNo) {
        return ResponseEntity.ok(postService.getPostsOrderByLikeCount(pageNo));
    }
}
