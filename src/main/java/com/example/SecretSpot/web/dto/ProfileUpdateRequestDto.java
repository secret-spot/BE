package com.example.SecretSpot.web.dto;

import com.example.SecretSpot.domain.Keyword;
import lombok.Data;

import java.util.List;

@Data
public class ProfileUpdateRequestDto {
    private String nickname;
    private String profileImageUrl;
    private List<String> keywords;
    private String language;
}
