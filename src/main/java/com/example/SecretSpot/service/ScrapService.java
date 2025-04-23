package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Scrap;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.domain.compositekeys.ScrapId;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.ScrapRepository;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScrapService {
    private final GuideRepository guideRepository;
    private final ScrapRepository scrapRepository;
    private final GuideMapper guideMapper;

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

    /**
     * 마이페이지에서 사용하는 최근 스크랩한 가이드 5개 반환 함수
     */
    public List<GuideCardItemDto> getMyScrapCards(User user) {
        List<Scrap> scraps = scrapRepository.findTop5ByUserIdOrderByCreatedAtDesc(user.getId());
        if (scraps.isEmpty()) return Collections.emptyList();

        List<Long> guideIds = scraps.stream()
                .map(scrap -> scrap.getGuide().getId())
                .toList();

        List<Guide> scrapedGuides = guideRepository.findAllByIdIn(guideIds);

        Map<Long, Guide> guideMap = scrapedGuides.stream()
                .collect(Collectors.toMap(Guide::getId, guide -> guide));

        List<Guide> sortedGuides = guideIds.stream()
                .map(guideMap::get)
                .filter(Objects::nonNull)
                .toList();

        Set<Long> scrapedIdSet = new HashSet<>(guideIds);

        return guideMapper.toCardDtos(sortedGuides, scrapedIdSet);
    }

    /**
     * 스크랩 페이지에서 사용하는 내가 스크랩한 가이드 전체 반환 함수
     * TODO 필요시 추후 페이징 기능 적용
     */
    public List<GuideListItemDto> getMyScrapList(User user) {
        List<Scrap> scraps = scrapRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        if (scraps.isEmpty()) return Collections.emptyList();

        List<Long> guideIds = scraps.stream()
                .map(scrap -> scrap.getGuide().getId())
                .toList();

        List<Guide> scrapedGuides = guideRepository.findAllByIdIn(guideIds);

        return guideMapper.toListDtos(scrapedGuides);
    }

    /**
     * 스크랩한 가이드 여부 확인
     */
    public boolean checkIsScraped(Long userId, Long guideId) {
        ScrapId scrapId = new ScrapId(userId, guideId);
        return scrapRepository.existsById(scrapId);
    }

    /**
     * 한 번에 여러 가이드 스크랩 여부 확인
     */
    public List<Long> getScrappedGuideIds(Long userId, List<Long> guideIds) {
        return scrapRepository
                .findAllById_UserIdAndId_GuideIdIn(userId, guideIds)
                .stream().map(s -> s.getGuide().getId())
                .toList();
    }
}