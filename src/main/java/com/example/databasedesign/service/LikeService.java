package com.example.databasedesign.service;

import com.example.databasedesign.common.error.CustomHttpException;
import com.example.databasedesign.entity.LikeAction;
import com.example.databasedesign.entity.Post;
import com.example.databasedesign.entity.User;
import com.example.databasedesign.repository.LikeRepository;
import com.example.databasedesign.repository.PostRepository;
import com.example.databasedesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.databasedesign.common.error.HttpExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likePost(String userId, Long postId) {
        likeRepository.findById(userId, postId)
                .ifPresent(like -> {
                    throw new CustomHttpException(ALREADY_LIKED);
                });
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomHttpException(NOT_VALID_USER_ID));
        Post post = postRepository.findByPostIdForUpdate(postId)
                .orElseThrow(() -> new CustomHttpException(NOT_VALID_POST_ID));
        LikeAction like = LikeAction.builder()
                .userId(user.getId())
                .postId(post.getId())
                .build();
        likeRepository.save(like);
        post.like();
    }
}
