package com.example.databasedesign.repository;

import java.time.LocalDateTime;

public interface PostQueryDto {
    long getId();
    String getNickname();
    String getTitle();
    String getContent();
    LocalDateTime getCreatedAt();
    long getLikeCount();
    long getIsLiked();
}
