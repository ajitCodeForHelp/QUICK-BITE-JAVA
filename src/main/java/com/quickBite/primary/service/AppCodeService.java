package com.quickBite.primary.service;

import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AppCodeDto;
import com.quickBite.primary.mapper.AppCodeMapper;
import com.quickBite.primary.pojo.AppCode;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppCodeService extends _BaseService {

    public String save(AppCodeDto.CreateAppCode request) throws BadRequestException {
        AppCode appCode = AppCodeMapper.MAPPER.mapToPojo(request);
        appCode = appCodeRepository.save(appCode);
        return appCode.getId();

    }

    public void update(String id, AppCodeDto.UpdateAppCode appCodeDto) throws BadRequestException {
        AppCode appCode = findById(id);
        appCode = AppCodeMapper.MAPPER.mapToPojo(appCode, appCodeDto);
        appCodeRepository.save(appCode);
    }

    public AppCodeDto.DetailAppCode get(String id) throws BadRequestException {
        AppCode appCode = findById(id);
        return AppCodeMapper.MAPPER.mapToDetailDto(appCode);
    }

    public List<AppCodeDto.DetailAppCode> list(String data) {
        // Data >  Active | Inactive  | All
        List<AppCode> list = null;
        if (data.equals("Active")) {
            list = appCodeRepository.findByActive(true);
        } else if (data.equals("Inactive")) {
            list = appCodeRepository.findByActive(false);
        } else {
            list = appCodeRepository.findAll();
        }
        return list.stream()
                .map(appCode -> AppCodeMapper.MAPPER.mapToDetailDto(appCode))
                .collect(Collectors.toList());
    }

//    public void activate(String id) throws BadRequestException {
//        AppCode appCode = findById(id);
//        appCode.setActive(true);
//        appCode.setModifiedAt(LocalDateTime.now());
//        appCodeRepository.save(appCode);
//    }
//
//    public void inactivate(String id) throws BadRequestException {
//        AppCode appCode = findById(id);
//        appCode.setActive(false);
//        appCode.setModifiedAt(LocalDateTime.now());
//        appCodeRepository.save(appCode);
//    }

    private AppCode findById(String id) throws BadRequestException {
        AppCode appCode = appCodeRepository.findById(new ObjectId(id)).orElse(null);
        if (appCode == null) {
            throw new BadRequestException("ecommerce.common.message.record_not_exist");
        }
        return appCode;
    }
}