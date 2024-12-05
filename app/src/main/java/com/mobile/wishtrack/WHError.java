package com.mobile.wishtrack;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class WHError extends RuntimeException{
    public WHError(ErrorMessage errorMessage) {
        super(errorMessage.message);
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorMessage {
        NAVER_SEARCH_ERROR("네이버 검색 오류"),
        NAVER_SEARCH_RESPONSE_ERROR("네이버 응답 오류");

        private final String message;
    }
}
