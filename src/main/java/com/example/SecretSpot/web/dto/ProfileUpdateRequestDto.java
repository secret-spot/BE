package com.example.SecretSpot.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfileUpdateRequestDto {
    private String nickname;
    private List<String> keywords;
    private String language;
}
