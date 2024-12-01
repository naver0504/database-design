package com.example.databasedesign.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "like_action")
@IdClass(LikePk.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class LikeAction {

    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    @Id
    @Column(name = "post_id")
    private Long postId;
}
