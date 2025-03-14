package com.quickBite.primary.controller.vendor;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.RestaurantDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/v1/restaurant")
public class VendorRestaurantController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@Valid @RequestBody RestaurantDto.CreateRestaurant request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Restaurant Saved Successfully.")
                .responsePacket(restaurantService.save(request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("id") String id, @Valid @RequestBody RestaurantDto.UpdateRestaurant request) throws BadRequestException {
        restaurantService.update(id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Restaurant Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Data Successfully.")
                .responsePacket(restaurantService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Successfully.")
                .responsePacket(restaurantService.list(data))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    protected ResponseEntity<ResponsePacket> activate(@PathVariable("id") String id) throws BadRequestException {
        restaurantService.activate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Restaurant Active Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/inactivate/{id}")
    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
        restaurantService.inactivate(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Restaurant Inactive Successfully.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/restaurant-on-off/{id}")
    protected ResponseEntity<ResponsePacket> restaurantOnOff(@PathVariable("id") String id) throws BadRequestException {
        restaurantService.restaurantOnOff(id);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Restaurant Status Updated.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-my-restaurant-id")
    protected ResponseEntity<ResponsePacket> getMyRestaurantId() throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get Data Successfully.")
                .responsePacket(restaurantService.getMyRestaurantId())
                .build(), HttpStatus.OK);
    }

}
