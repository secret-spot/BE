package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.QnaService;
import com.example.SecretSpot.web.dto.AnswerCreateDto;
import com.example.SecretSpot.web.dto.QnaItemDto;
import com.example.SecretSpot.web.dto.QuestionCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @PostMapping("/api/guides/{guideId}/qnas")
    public ResponseEntity<QnaItemDto> createQuestion(@PathVariable Long guideId,
                                                     @RequestBody QuestionCreateDto questionCreateDto,
                                                     @AuthenticationPrincipal User user) {
        QnaItemDto responseDto = qnaService.createQuestion(guideId, questionCreateDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PatchMapping("/api/guides/{guideId}/qnas/{questionId}/answer")
    public ResponseEntity<QnaItemDto> createAnswer(@PathVariable Long guideId,
                                                   @PathVariable Long questionId,
                                                   @RequestBody AnswerCreateDto answerCreateDto,
                                                   @AuthenticationPrincipal User user) {
        QnaItemDto responseDto = qnaService.createAnswer(guideId, questionId, answerCreateDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/api/guides/{guideId}/qnas")
    public ResponseEntity<List<QnaItemDto>> getQnas(@PathVariable Long guideId, @AuthenticationPrincipal User user) {
        List<QnaItemDto> responseDto = qnaService.getQnas(guideId, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
