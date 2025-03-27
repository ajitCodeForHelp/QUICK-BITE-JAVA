//package com.quickBite.primary.controller.admin;
//
//import com.quickBite.bean.ResponsePacket;
//import com.quickBite.exception.BadRequestException;
//import com.quickBite.primary.controller._BaseController;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/admin/v1/customer")
//public class AdminCustomerController extends _BaseController {
//
//    @GetMapping("/list-data/{data}")
//    protected ResponseEntity<ResponsePacket> listData(@PathVariable("data") String data) {
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("Customer List Data Successfully.")
//                .responsePacket(customerService.listData(data))
//                .build(), HttpStatus.OK);
//    }
//
//    @PutMapping("/activate/{uuid}")
//    protected ResponseEntity<ResponsePacket> activate(@PathVariable("uuid") String uuid) throws BadRequestException {
//        customerService.activate(uuid);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("Customer Activated Successfully.")
//                .build(), HttpStatus.OK);
//    }
//
//    @PutMapping("/inactivate/{uuid}")
//    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("uuid") String uuid) throws BadRequestException {
//        customerService.inactivate(uuid);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("Customer InActivated Successfully.")
//                .build(), HttpStatus.OK);
//    }
//
//}
