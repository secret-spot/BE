package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Scrap;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.domain.compositekeys.ScrapId;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.ScrapRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ScrapService {
    private final GuideRepository guideRepository;
    private final ScrapRepository scrapRepository;

    /**
     * 스크랩 함수
     */
    public void createScrap(Long guideId, User user) throws BadRequestException {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Guide, id=" + guideId));

        ScrapId scrapId = new ScrapId(user.getId(), guideId);

        if (!scrapRepository.existsById(scrapId)) {
            Scrap scrap = new Scrap();
            scrap.setId(scrapId);
            scrap.setGuide(guide);
            scrap.setUser(user);

            scrapRepository.save(scrap);
        } else {
            throw new BadRequestException("이미 스크랩한 가이드입니다.");
        }
    }

    /**
     * 스크랩 취소 함수
     */
    public void deleteScrap(Long guideId, User user) {
        ScrapId scrapId = new ScrapId(user.getId(), guideId);
        scrapRepository.deleteById(scrapId);
    }
}