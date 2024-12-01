package com.example.databasedesign.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;
    @Column(length = 1000)
    private String content;
    private LocalDateTime createdAt;

    private long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public void like() {
        likeCount++;
    }


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        likeCount = 0L;
    }
}
