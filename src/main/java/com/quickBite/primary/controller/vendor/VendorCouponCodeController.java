package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.CouponCodeDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/{restaurantId}/coupon-code")
public class VendorCouponCodeController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @Valid @RequestBody CouponCodeDto.CreateCouponCode request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Coupon Code Saved Successfully.")
                .responsePacket(couponCodeService.save(restaurantId, request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id,
                                                 @Valid @RequestBody CouponCodeDto.UpdateCouponCode request) throws BadRequestException {
        couponCodeService.update(restaurantId, id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Coupon Code Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Coupon Code Data Successfully.")
                .responsePacket(couponCodeService.get(restaurantId, id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("restaurantId") ObjectId restaurantId,
                                                  @PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(couponCodeService.list(restaurantId, data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                      @PathVariable("id") String id) throws BadRequestException {
        couponCodeService.activate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Coupon Code Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                        @PathVariable("id") String id) throws BadRequestException {
        couponCodeService.inactivate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Coupon Code InActivate Successfully.")
                .build(), HttpStatus.OK);
    }

}
