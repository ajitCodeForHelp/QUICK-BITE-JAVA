package com.quickBite.primary.controller.admin;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.VendorDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
                .message("Vendor Saved.")
                .responsePacket(vendorService.save(vendor))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("id") String id, @Valid @RequestBody VendorDto.UpdateVendor vendor) throws BadRequestException {
        vendorService.update(id, vendor);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor Updated.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Details")
                .responsePacket(vendorService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Data List.")
                .responsePacket(vendorService.list(data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("id") String id) throws BadRequestException {
        vendorService.activate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor Activate.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
        vendorService.inactivate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor InActivate.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<ResponsePacket> resetPassword(@PathVariable("id") String id,
                                                        @Valid @RequestBody VendorDto.ResetPassword request) throws BadRequestException {
        vendorService.resetPassword(id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Password Reset Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/vendor-key-list")
    public ResponseEntity<ResponsePacket> vendorKeyList() {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor key Value List.")
                .responsePacket(vendorService.vendorKeyList())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/vendor-restaurant-key-list/{vendorId}")
    public ResponseEntity<ResponsePacket> vendorRestaurantKeyList(
            @PathVariable("vendorId") ObjectId vendorId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Vendor Restaurant key Value List.")
                .responsePacket(restaurantService.vendorRestaurantKeyList(vendorId))
                .build(), HttpStatus.OK);
    }

}
