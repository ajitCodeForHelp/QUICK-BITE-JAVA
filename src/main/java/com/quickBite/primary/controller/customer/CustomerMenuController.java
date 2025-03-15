package com.quickBite.primary.controller.customer;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/customer/v1/menu")
public class CustomerMenuController extends _BaseController {

    @GetMapping("/get-menu/{appCodeId}")
    protected ResponseEntity<ResponsePacket> getMenu(@PathVariable("appCodeId") String appCodeId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .responsePacket(menuDataService.getMenu(appCodeId))
                .errorCode(0)
                .message("Get Menu Successfully.")
                .build(), HttpStatus.OK);
    }
}
