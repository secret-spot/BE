package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Qna;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.QnaRepository;
import com.example.SecretSpot.web.dto.AnswerCreateDto;
import com.example.SecretSpot.web.dto.QnaItemDto;
import com.example.SecretSpot.web.dto.QuestionCreateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final GuideRepository guideRepository;

    /**
     * 특정 가이드의 QNA 정보 불러오기 함수
     */
    public List<QnaItemDto> getQnas(Long guideId, User user) {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Guide, id=" + guideId));

        User guideOwner = guide.getUser();
        boolean isOwner = guideOwner.getId().equals(user.getId());

        List<Qna> qnaList = qnaRepository.findByGuideId(guideId);

        return qnaList.stream()
                .map(qna -> {
                    boolean hasAnswer = qna.getAnswer() != null;
                    return QnaItemDto.builder()
                            .id(qna.getId())
                            .question(qna.getQuestion())
                            .answer(qna.getAnswer())
                            .isMine(user.getId().equals(qna.getUser().getId()))
                            .isGuideOwner(isOwner)
                            .questionerId(qna.getUser().getId())
                            .questionerProfileImageUrl(qna.getUser().getProfileImageUrl())
                            .answererId(hasAnswer ? guideOwner.getId() : null)
                            .answererProfileImageUrl(hasAnswer ? guideOwner.getProfileImageUrl() : null)
                            .createdAt(qna.getCreatedAt())
                            .build();
                })
                .toList();
    }


    /**
     * 특정 가이드에 질문 작성하는 함수
     */
    public QnaItemDto createQuestion(Long guideId, QuestionCreateDto questionCreateDto, User user) {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Guide, id=" + guideId));

        if (guide.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("가이드 작성자는 질문을 작성할 수 없습니다.");
        }

        Qna qna = new Qna();
        qna.setQuestion(questionCreateDto.getQuestion());
        qna.setUser(user);
        qna.setGuide(guide);

        qnaRepository.save(qna);

        return QnaItemDto.builder()
                .id(qna.getId())
                .question(qna.getQuestion())
                .answer(null)
                .isMine(true)
                .isGuideOwner(false)
                .questionerId(user.getId())
                .questionerProfileImageUrl(user.getProfileImageUrl())
                .answererId(null)
                .answererProfileImageUrl(null)
                .createdAt(qna.getCreatedAt())
                .build();
    }


    /**
     * 특정 가이드에 질문 답변하는 함수
     */
    public QnaItemDto createAnswer(Long guideId, Long questionId, AnswerCreateDto answerCreateDto, User user) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Qna, id=" + questionId));

        Guide guide = qna.getGuide();

        if (!guide.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("가이드 작성자만 답변을 작성할 수 있습니다.");
        }

        qna.setAnswer(answerCreateDto.getAnswer());
        qnaRepository.save(qna);

        return QnaItemDto.builder()
                .id(qna.getId())
                .question(qna.getQuestion())
                .answer(qna.getAnswer())
                .isMine(false)
                .isGuideOwner(true)
                .questionerId(qna.getUser().getId())
                .questionerProfileImageUrl(qna.getUser().getProfileImageUrl())
                .answererId(user.getId())
                .answererProfileImageUrl(user.getProfileImageUrl())
                .createdAt(qna.getCreatedAt())
                .build();
    }
}