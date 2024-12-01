package com.example.databasedesign.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikePk implements Serializable {
    private Long postId;
    private String userId;
}