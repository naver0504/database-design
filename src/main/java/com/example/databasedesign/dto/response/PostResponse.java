package com.example.databasedesign.dto.response;

import com.example.databasedesign.repository.PostQueryDto;

import java.time.LocalDate;

public record PostResponse(
        long id,
        String nickname,
        String title,
        String content,
        LocalDate createdAt,
        long likeCount,
        boolean isLiked) {

    public PostResponse(PostQueryDto postQueryDto) {
        this(postQueryDto.getId(), postQueryDto.getNickname(), postQueryDto.getTitle(), postQueryDto.getContent(),
                postQueryDto.getCreatedAt().toLocalDate(), postQueryDto.getLikeCount(), postQueryDto.getIsLiked() == 1);
    }
}
