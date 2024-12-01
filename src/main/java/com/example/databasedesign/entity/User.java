package com.example.databasedesign.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id @Column(length = 20)
    private String id;

    @Column(length = 20)
    private String nickname;

    @Column(length = 30)
    private String password;
}
