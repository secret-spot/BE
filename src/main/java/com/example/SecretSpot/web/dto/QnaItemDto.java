package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaItemDto {
    private Long id;
    private String question;
    private String answer;
    private Boolean isMine; // 접속한 유저가 질문 작성자인지 여부
    private Boolean isGuideOwner; // 접속한 유저가 가이드 작성자인지(답변 작성 가능자인지) 여부
    private Long questionerId;
    private String questionerProfileImageUrl;
    private Long answererId;
    private String answererProfileImageUrl;
    private LocalDateTime createdAt;
}
