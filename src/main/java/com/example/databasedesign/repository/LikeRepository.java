package com.example.databasedesign.repository;

import com.example.databasedesign.entity.LikeAction;
import com.example.databasedesign.entity.LikePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<LikeAction, LikePk> {

    @Query("SELECT l FROM like_action l WHERE l.userId = :userId AND l.postId = :postId")
    Optional<LikeAction> findById(String userId, Long postId);
}
