package com.quickBite.primary.service;

import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.BannerDto;
import com.quickBite.primary.mapper.BannerMapper;
import com.quickBite.primary.pojo.Banner;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BannerService extends _BaseService {

    public String save(BannerDto.CreateBanner bannerDto) throws BadRequestException {
        Banner banner = BannerMapper.MAPPER.mapToPojo(bannerDto);
        banner = bannerRepository.save(banner);
        return banner.getId();

    }

    public void update(String id, BannerDto.UpdateBanner bannerDto) throws BadRequestException {
        Banner banner = findById(id);
        banner = BannerMapper.MAPPER.mapToPojo(banner, bannerDto);
        bannerRepository.save(banner);
    }

    public BannerDto.DetailBanner get(String id) throws BadRequestException {
        Banner banner = findById(id);
        return BannerMapper.MAPPER.mapToDetailDto(banner);
    }

    public List<BannerDto.DetailBanner> list(String data) {
        // Data >  Active | Inactive  | All
        List<Banner> list = null;
        if (data.equals("Active")) {
            list = bannerRepository.findByActive(true);
        } else if (data.equals("Inactive")) {
            list = bannerRepository.findByActive(false);
        } else {
            list = bannerRepository.findAll();
        }
        return list.stream()
                .map(banner -> BannerMapper.MAPPER.mapToDetailDto(banner))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Banner banner = findById(id);
        banner.setActive(true);
        banner.setModifiedAt(LocalDateTime.now());
        bannerRepository.save(banner);
    }

    public void inactivate(String id) throws BadRequestException {
        Banner banner = findById(id);
        banner.setActive(false);
        banner.setModifiedAt(LocalDateTime.now());
        bannerRepository.save(banner);
    }

    private Banner findById(String id) throws BadRequestException {
        Banner banner = bannerRepository.findById(new ObjectId(id)).orElse(null);
        if (banner == null) {
            throw new BadRequestException("ecommerce.common.message.record_not_exist");
        }
        return banner;
    }
}