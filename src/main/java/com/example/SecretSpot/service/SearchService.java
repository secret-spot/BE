package com.example.SecretSpot.service;

import com.example.SecretSpot.client.SearchClient;
import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuideRegion;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.GuideRegionRepository;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.RegionRepository;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import com.example.SecretSpot.web.dto.NearbyRegionItemDto;
import com.example.SecretSpot.web.dto.RegionRecommendWithCommentDto;
import com.example.SecretSpot.web.dto.SearchResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchClient searchClient;
    private final GuideMapper guideMapper;
    private final RegionRepository regionRepository;
    private final GuideRepository guideRepository;
    private final GuideRegionRepository guideRegionRepository;

    public SearchResponseDto searchGuides(String query) {
        String trimmedQuery = query.trim();
        Boolean isRegion = searchClient.checkIsRegion(trimmedQuery).getIsRegion();

        List<GuideListItemDto> formattedGuides = null;

        if (isRegion) {
            Region region = regionRepository.findByName(trimmedQuery).orElse(null);

            //지역이긴 하지만 DB에 없는 지역일 경우
            if (region == null) {
                //TODO 추후 대한민국이 아닌 다른 나라일 경우를 고려해서 확장해야함
                //TODO AI가 지역이 아닌걸 지역으로 판단했을 때는 어떻게 처리할것인지?
                Region newRegion = new Region();
                newRegion.setName(trimmedQuery);
                newRegion.setCountry("대한민국");
                regionRepository.save(newRegion);
                region = newRegion;
            } else {
                //DB에 있는 지역일 때만 guides 찾기
                List<Guide> guides = findGuidesByRegionName(trimmedQuery);
                formattedGuides = guideMapper.toListDtos(guides);
            }

            //에티켓
            String etiquette = region.getEtiquette();
            if (etiquette == null) {
                etiquette = searchClient.createEtiquette(trimmedQuery);
                region.setEtiquette(etiquette);
                regionRepository.save(region);
            }

            //근처 소도시
            List<NearbyRegionItemDto> nearbyCities = searchClient.recommendNearbyCities(trimmedQuery);
            List<RegionRecommendWithCommentDto> formattedNearbyCities = nearbyCities.stream()
                    .map(item -> RegionRecommendWithCommentDto.builder()
                            .region(item.getSmallCity())
                            .review(item.getShortReview())
                            .build())
                    .toList();

            return SearchResponseDto.builder()
                    .keyword(trimmedQuery)
                    .isRegion(true)
                    .etiquette(etiquette)
                    .recommendations(formattedNearbyCities)
                    .guides(formattedGuides)
                    .build();
        } else {
            List<Guide> guides = guideRepository.searchByKeyword(trimmedQuery);
            formattedGuides = guideMapper.toListDtos(guides);
            return SearchResponseDto.builder()
                    .keyword(trimmedQuery)
                    .isRegion(false)
                    .guides(formattedGuides)
                    .build();
        }
    }

    public List<Guide> findGuidesByRegionName(String regionName) {
        Region region = regionRepository.findByName(regionName)
                .orElseThrow(() -> new EntityNotFoundException("Not found region: " + regionName));

        List<GuideRegion> guideRegions = guideRegionRepository.findByRegion_Id(region.getId());

        return guideRegions.stream()
                .map(GuideRegion::getGuide)
                .toList();
    }
}
