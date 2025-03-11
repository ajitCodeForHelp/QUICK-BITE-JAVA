package com.quickBite.primary.controller;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends _BaseController {

    @PostMapping("/admin-login")
    public ResponseEntity<ResponsePacket> adminLogin(HttpServletRequest request, @Valid @RequestBody AuthDto.AdminLogin login)
            throws BadRequestException {
        AuthDto.UserDetails userAdminAdminDetail = authService.loginAdmin(login.getUserName(), login.getPassword(), request.getRemoteAddr());
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Login Successfully.")
                .responsePacket(userAdminAdminDetail)
                .build(), HttpStatus.OK);
    }

//    @PostMapping("/vendor-login")
//    public ResponseEntity<ResponsePacket> vendorLogin(HttpServletRequest request, @Valid @RequestBody AuthDto.VendorLogin login)
//            throws BadRequestException {
//        AuthDto.UserDetails userClientDetail = authTrade.loginClient(login.getUserName(), login.getPassword(), request.getRemoteAddr());
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("Login Successfully.")
//                .responsePacket(userClientDetail)
//                .build(), HttpStatus.OK);
//    }

}