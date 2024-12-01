package com.example.databasedesign.dto.response;

public record CheckUserIdResponse(boolean isExist) {

    public CheckUserIdResponse(long isExist) {
        this(isExist == 1);
    }
}
