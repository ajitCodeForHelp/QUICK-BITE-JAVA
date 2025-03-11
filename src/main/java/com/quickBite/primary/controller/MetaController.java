//package com.quickBite.primary.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/meta")
//public class MetaController {
//
//    @PostMapping("/get-menu-qr-code/{tableQRCode}")
//    public @ResponseBody
//    ResponseEntity<ResponsePacket> getTableQrCodeImage(
//            HttpServletRequest request, @PathVariable String tableQRCode) {
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .responsePacket(QRCodeUtils.getInstance().getTableQRCode(request, tableQRCode))
//                .build(), HttpStatus.OK);
//    }
//
//}