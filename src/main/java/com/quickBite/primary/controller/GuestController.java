package com.quickBite.primary.controller;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.VendorDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/guest")
public class GuestController extends _BaseController {

    @PostMapping("/create-vendor")
    public ResponseEntity<ResponsePacket> createVendor(@Valid @RequestBody VendorDto.CreateVendorRequest request) throws BadRequestException {
        vendorService.createVendor(request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor Creation Request Process Successfully.")
                .build(), HttpStatus.OK);
    }

}
