package com.quickBite.primary.controller.admin;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.VendorDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/v1/vendor")
public class AdminVendorController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@Valid @RequestBody VendorDto.CreateVendor vendor) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.save")
                .responsePacket(vendorService.save(vendor))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("id") String id, @Valid @RequestBody VendorDto.UpdateVendor vendor) throws BadRequestException {
        vendorService.update(id, vendor);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.update")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.active")
                .responsePacket(vendorService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.get_all")
                .responsePacket(vendorService.list(data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("id") String id) throws BadRequestException {
        vendorService.activate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.active")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
        vendorService.inactivate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("ecommerce.common.message.inactive")
                .build(), HttpStatus.OK);
    }

}
