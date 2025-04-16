//package com.quickBite.primary.controller.customer;
//
//import com.bt.ecommerce.annotation.TranslateResponseMessage;
//import com.bt.ecommerce.bean.ResponsePacket;
//import com.bt.ecommerce.exception.BadRequestException;
//import com.bt.ecommerce.primary.controller._BaseController;
//import com.bt.ecommerce.primary.dto.CustomerDto;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/customer/v1")
//public class CustomerController extends _BaseController {
//
//    @TranslateResponseMessage
//    @PutMapping("/update-profile")
//    protected ResponseEntity<ResponsePacket> updateProfile(
//            @Valid @RequestBody CustomerDto.UpdateCustomer update) throws BadRequestException {
//        customerService.updateProfile(update);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("ecommerce.common.message.update")
//                .build(), HttpStatus.OK);
//    }
//
//    @TranslateResponseMessage
//    @GetMapping("/get")
//    protected ResponseEntity<ResponsePacket> get() throws BadRequestException {
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("ecommerce.common.message.get")
//                .responsePacket(customerService.get())
//                .build(), HttpStatus.OK);
//    }
//}
