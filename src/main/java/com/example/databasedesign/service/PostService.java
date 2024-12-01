package com.example.databasedesign.service;

import com.example.databasedesign.common.error.CustomHttpException;
import com.example.databasedesign.dto.request.CreatePostRequest;
import com.example.databasedesign.dto.response.PostResponse;
import com.example.databasedesign.entity.Post;
import com.example.databasedesign.entity.User;
import com.example.databasedesign.repository.PostQueryDto;
import com.example.databasedesign.repository.PostRepository;
import com.example.databasedesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.databasedesign.common.error.HttpExceptionMessage.NOT_VALID_USER_ID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private static final int PAGE_SIZE = 30;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(String userId, CreatePostRequest createPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomHttpException(NOT_VALID_USER_ID));

        Post post = Post.builder()
                .title(createPostRequest.title())
                .content(createPostRequest.content())
                .user(user)
                .build();

        postRepository.save(post);
    }

    public List<PostResponse> getPosts(Long lastId) {
        lastId = getLastId(lastId);
        List<PostQueryDto> posts = postRepository.findPosts(lastId);
        return posts.stream()
                .map(PostResponse::new)
                .toList();
    }

    public List<PostResponse> getUserLikedPosts(Integer pageNo, String userId) {
        if (userRepository.existsByUserId(userId) == 0) {
            throw new CustomHttpException(NOT_VALID_USER_ID);
        }

        return postRepository.findUserLikedPosts(userId, getOffset(pageNo)).stream()
                .map(PostResponse::new)
                .toList();
    }

    public List<PostResponse> getPostsOrderByLikeCount(Integer pageNo) {
        return postRepository.findPostsOrderByLikeCount(getOffset(pageNo)).stream()
                .map(PostResponse::new)
                .toList();
    }

    private long getLastId(Long lastId) {
        return lastId == null ? Long.MAX_VALUE : lastId;
    }

    private long getOffset(Integer pageNo) {
        return pageNo == null ? 0 : (long) pageNo * PAGE_SIZE;
    }
}