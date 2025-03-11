package com.quickBite.primary.controller.admin;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.SettingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/v1/setting")
public class AdminSettingController extends _BaseController {


    @GetMapping("/setting-list")
    public ResponseEntity<ResponsePacket> settingList() throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Setting details.")
                .responsePacket(settingService.settingList())
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update-settings")
    public ResponseEntity<ResponsePacket> updateSettings(
            @RequestBody SettingDto.UpdateSettings updateSettings) throws BadRequestException {
        settingService.updateSettings(updateSettings);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Setting updated.")
                .build(), HttpStatus.OK);
    }

}