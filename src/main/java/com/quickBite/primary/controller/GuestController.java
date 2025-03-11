//package com.quickBite.primary.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequestMapping("/guest")
//public class GuestController extends _BaseController {
//
//    /**
//     * [GET] /guest/country-list
//     * [GET] /guest/state-list
//     * [GET] /guest/city-list
//     */
//
//    @GetMapping("/country-list")
//    public ResponseEntity<ResponsePacket> countryList() {
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("kpis_food.common.message.get_all")
//                .responsePacket(localCountryTrade.getCountryList())
//                .build(), HttpStatus.OK);
//    }
//
//}
