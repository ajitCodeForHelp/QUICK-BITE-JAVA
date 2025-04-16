package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.ItemDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/{restaurantId}/item")
public class VendorItemController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@PathVariable("restaurantId") ObjectId restaurantId,
                                               @Valid @RequestBody ItemDto.CreateItem request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Item Saved Successfully.")
                .responsePacket(itemService.save(restaurantId, request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id, @Valid @RequestBody ItemDto.UpdateItem request) throws BadRequestException {
        itemService.update(restaurantId, id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Item Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("restaurantId") ObjectId restaurantId,
                                                 @PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Item Successfully.")
                .responsePacket(itemService.get(restaurantId, id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("restaurantId") ObjectId restaurantId,
                                                  @PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Item List Data Successfully.")
                .responsePacket(itemService.list(restaurantId, data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                      @PathVariable("id") String id) throws BadRequestException {
        itemService.activate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Item Activate Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("restaurantId") ObjectId restaurantId,
                                                        @PathVariable("id") String id) throws BadRequestException {
        itemService.inactivate(restaurantId, id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Item Inactivate Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/key-value-list")
    protected ResponseEntity<ResponsePacket> keyValueList(@PathVariable("restaurantId") ObjectId restaurantId) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Key List Data Successfully.")
                .responsePacket(itemService.itemKeyValueList(restaurantId))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/generate-menu")
    protected ResponseEntity<ResponsePacket> generateMenu() throws BadRequestException {
        menuDataService.menuBuilder();
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Menu Generated Successfully.")
                .build(), HttpStatus.OK);
    }

}
