package com.quickBite.primary.controller;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.CustomerDto;
import com.quickBite.primary.dto.VendorDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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

    @PostMapping("/generate-otp")
    protected ResponseEntity<ResponsePacket> generateOtp(@Valid @RequestBody CustomerDto.GenerateOtp generateOtp) {
        oneTimePasswordService.generateOtp(generateOtp);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Otp Generated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}/get-customer-menu")
    protected ResponseEntity<ResponsePacket> getMenu(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .responsePacket(menuDataService.getMenu(restaurantId))
                .errorCode(0)
                .message("Get Menu Successfully.")
                .build(), HttpStatus.OK);
    }


}
