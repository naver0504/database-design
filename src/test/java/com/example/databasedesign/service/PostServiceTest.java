package com.example.databasedesign.service;

import com.example.databasedesign.entity.Post;
import com.example.databasedesign.entity.User;
import com.example.databasedesign.repository.PostRepository;
import com.example.databasedesign.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    EntityManager em;

    @Test
    public void 게시글_좋아요_락_적용_테스트() throws InterruptedException {
        long postId = 10L;
        int numOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    likeService.likePost("testId" + finalI , postId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(1000);
        postRepository.findById(postId).ifPresent(post -> {
            System.out.println("좋아요 수: " + post.getLikeCount());
            assertEquals(numOfThreads, post.getLikeCount());
        });
    }
}