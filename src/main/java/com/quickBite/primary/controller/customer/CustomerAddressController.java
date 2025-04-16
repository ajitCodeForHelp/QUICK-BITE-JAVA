package com.quickBite.primary.controller.customer;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.AddressDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer/v1/address")
public class CustomerAddressController extends _BaseController {

    @PostMapping("/save")
    protected ResponseEntity<ResponsePacket> save(@Valid @RequestBody AddressDto.SaveAddress create) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Customer Address Saved Successfully.")
                .responsePacket(addressService.save(create))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    protected ResponseEntity<ResponsePacket> edit(@PathVariable("id") String id,
                                                  @Valid @RequestBody AddressDto.UpdateAddress update) throws BadRequestException {
        addressService.update(id, update);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Customer Address Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Customer Address Saved Successfully.")
                .responsePacket(addressService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> listData(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Customer Address List Data Successfully.")
                .responsePacket(addressService.listAllCustomerAddress(data))
                .build(), HttpStatus.OK);
    }


    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
        addressService.inactivate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Customer Address Removed Successfully.")
                .build(), HttpStatus.OK);
    }
}
