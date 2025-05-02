package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCreateDto {
    private String question;
}
