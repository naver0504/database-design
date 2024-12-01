package com.example.databasedesign.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpExceptionMessage {

    NOT_VALID_USER_ID("존재하지 않는 Id 입니다."),
    NOT_VALID_PASSWORD("비밀번호가 일치하지 않습니다."),
    NOT_VALID_POST_ID("존재하지 않는 게시글 Id 입니다."),
    ALREADY_LIKED("이미 좋아요를 누른 게시글입니다.");

    private final String message;
}
