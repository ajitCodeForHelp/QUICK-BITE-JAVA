package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.CategoryDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/{restaurantId}/category")
public class VendorCategoryController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @Valid @RequestBody CategoryDto.CreateCategory request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Category Saved Successfully.")
                .responsePacket(categoryService.save(restaurantId, request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id,
                                                 @Valid @RequestBody CategoryDto.UpdateCategory request) throws BadRequestException {
        categoryService.update(restaurantId, id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Category Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Category Data Successfully.")
                .responsePacket(categoryService.get(restaurantId, id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("restaurantId") ObjectId restaurantId,
                                                  @PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Category List Data Successfully.")
                .responsePacket(categoryService.list(restaurantId, data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                      @PathVariable("id") String id) throws BadRequestException {
        categoryService.activate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Category Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                        @PathVariable("id") String id) throws BadRequestException {
        categoryService.inactivate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Category InActive Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/category-key-value-list")
    protected ResponseEntity<ResponsePacket> categoryKeyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(categoryService.categoryKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/parent-key-value-list")
    protected ResponseEntity<ResponsePacket> parentKeyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(categoryService.parentCategoryKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/sub-category-key-value-list")
    protected ResponseEntity<ResponsePacket> subCategoryKeyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(categoryService.subCategoryKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

}
