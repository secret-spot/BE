package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.repository.RegionRepository;
import com.example.SecretSpot.web.dto.RegionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getRegions(List<RegionDto> regions) {
        List<Region> regionList = new ArrayList<>();
        for (RegionDto regionDto : regions) {
            String country = regionDto.getCountry();
            String regionName = regionDto.getRegion();

            Region region = regionRepository.findByName(regionName).orElse(null);
            if (region == null) {
                region = Region.builder().name(regionName).country(country).build();
                regionRepository.save(region);
                regionRepository.flush();
            }
            regionList.add(region);
        }
        return regionList;
    }
}
