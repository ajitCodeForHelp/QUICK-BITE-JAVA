package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.ItemAddOnDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/itemAddOn")
public class VendorItemAddOnController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@Valid @RequestBody ItemAddOnDto.CreateItemAddOn request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Saved Successfully.")
                .responsePacket(itemAddOnService.save(request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("id") String id, @Valid @RequestBody ItemAddOnDto.UpdateItemAddOn request) throws BadRequestException {
        itemAddOnService.update(id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get AddOn Successfully.")
                .responsePacket(itemAddOnService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    public ResponseEntity<ResponsePacket> list(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get AddOn List Data Successfully.")
                .responsePacket(itemAddOnService.list(data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ResponsePacket> activate(@PathVariable("id") String id) throws BadRequestException {
        itemAddOnService.activate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    public ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
        itemAddOnService.inactivate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("AddOn Inactivate Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/key-value-list")
    public ResponseEntity<ResponsePacket> keyValueList() throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Key List Data Successfully.")
                .responsePacket(itemAddOnService.itemAddOnKeyValueList())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/category-wise-key-value-list")
    public ResponseEntity<ResponsePacket> categoryWiseKeyValueList(@Valid @RequestBody ItemAddOnDto.CategoryWiseItemAddOn request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Key List Data Successfully.")
                .responsePacket(itemAddOnService.categoryWiseKeyValueList(request.getSelectedCategoryList()))
                .build(), HttpStatus.OK);
    }

}
