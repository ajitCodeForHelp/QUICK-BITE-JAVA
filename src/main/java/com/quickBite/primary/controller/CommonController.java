//package com.quickBite.primary.controller;
//
//import com.kpis.kpisFood.primary.dto.CommonUserDto;
//import com.kpis.kpisFood.primary.mapper.CommonUserMapper;
//import com.kpis.kpisFood.primary.pojo.user.UserAdmin;
//import com.kpis.kpisFood.primary.pojo.user.UserClient;
//import com.kpis.kpisFood.primary.pojo.user.UserRestaurant;
//import com.kpis.kpisFood.primary.pojo.user._BaseUser;
//import com.kpis.kpisFood.security.JwtUserDetailsService;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/ops/v1/common-user")
//public class CommonController extends _BaseController {
//
//    @TranslateResponseMessage
//    @GetMapping("/profile-details")
//    public ResponseEntity<ResponsePacket> getProfileDetails() throws BadRequestException {
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("kpis_food.common.message.get")
//                .responsePacket(commonTrade.getProfileDetails())
//                .build(), HttpStatus.OK);
//    }
//
//    @TranslateResponseMessage
//    @PutMapping("/change-password")
//    public ResponseEntity<ResponsePacket> changePassword(
//            @Valid @RequestBody CommonUserDto.ResetPasswordRequest request) throws BadRequestException {
//        commonTrade.changePassword(request);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("kpis_food.common.message.password_changed")
//                .build(), HttpStatus.OK);
//    }
//
//    @TranslateResponseMessage
//    @GetMapping("/logout")
//    public ResponseEntity<ResponsePacket> logout() throws BadRequestException {
//        commonTrade.logout();
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("kpis_food.common.message.logout")
//                .build(), HttpStatus.OK);
//    }
//
//}