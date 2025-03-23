package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.ItemAddOnDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/{restaurantId}/itemAddOn")
public class VendorItemAddOnController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @Valid @RequestBody ItemAddOnDto.CreateItemAddOn request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Saved Successfully.")
                .responsePacket(itemAddOnService.save(restaurantId, request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id, @Valid @RequestBody ItemAddOnDto.UpdateItemAddOn request) throws BadRequestException {
        itemAddOnService.update(restaurantId, id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponsePacket> get(@PathVariable("restaurantId") ObjectId restaurantId,
                                              @PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get AddOn Successfully.")
                .responsePacket(itemAddOnService.get(restaurantId, id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    public ResponseEntity<ResponsePacket> list(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get AddOn List Data Successfully.")
                .responsePacket(itemAddOnService.list(restaurantId, data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ResponsePacket> activate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                   @PathVariable("id") String id) throws BadRequestException {
        itemAddOnService.activate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    public ResponseEntity<ResponsePacket> inactivate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                     @PathVariable("id") String id) throws BadRequestException {
        itemAddOnService.inactivate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Inactivate Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/key-value-list")
    public ResponseEntity<ResponsePacket> keyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Key List Data Successfully.")
                .responsePacket(itemAddOnService.itemAddOnKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/category-wise-key-value-list")
    public ResponseEntity<ResponsePacket> categoryWiseKeyValueList(@PathVariable("restaurantId") ObjectId restaurantId,
                                                                   @Valid @RequestBody ItemAddOnDto.CategoryWiseItemAddOn request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Key List Data Successfully.")
                .responsePacket(itemAddOnService.categoryWiseKeyValueList(restaurantId, request.getSelectedCategoryList()))
                .build(), HttpStatus.OK);
    }

}
