package com.example.SecretSpot.web.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GuideDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String content;
    private List<String> images;
    private List<PlaceDto> places;
}
