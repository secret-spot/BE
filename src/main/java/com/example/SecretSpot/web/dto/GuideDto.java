package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class GuideDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String content;
    private List<PlaceDto> places;
}
