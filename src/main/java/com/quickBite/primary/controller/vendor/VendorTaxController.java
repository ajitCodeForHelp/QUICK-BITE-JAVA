package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.TaxDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/{restaurantId}/tax")
public class VendorTaxController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @Valid @RequestBody TaxDto.CreateTax request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Tax Saved Successfully.")
                .responsePacket(taxService.save(restaurantId, request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id, @Valid @RequestBody TaxDto.UpdateTax request) throws BadRequestException {
        taxService.update(restaurantId, id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Tax Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Tax Data Successfully.")
                .responsePacket(taxService.get(restaurantId, id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("restaurantId") ObjectId restaurantId,
                                                  @PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Tax List Data Successfully.")
                .responsePacket(taxService.list(restaurantId, data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                      @PathVariable("id") String id) throws BadRequestException {
        taxService.activate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Tax Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                        @PathVariable("id") String id) throws BadRequestException {
        taxService.inactivate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Tax InActive Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/tax-key-value-list")
    protected ResponseEntity<ResponsePacket> taxKeyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(taxService.taxKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

}
