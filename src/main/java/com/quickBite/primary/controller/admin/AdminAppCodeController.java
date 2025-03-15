package com.quickBite.primary.controller.admin;

import com.quickBite.bean.ResponsePacket;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.controller._BaseController;
import com.quickBite.primary.dto.AppCodeDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/v1/app-code")
public class AdminAppCodeController extends _BaseController {

    @PostMapping("/save")
    public ResponseEntity<ResponsePacket> save(@Valid @RequestBody AppCodeDto.CreateAppCode request) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("App Code Saved Successfully.")
                .responsePacket(appCodeService.save(request))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePacket> update(@PathVariable("id") String id, @Valid @RequestBody AppCodeDto.UpdateAppCode request) throws BadRequestException {
        appCodeService.update(id, request);
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("App Code Updated Successfully.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    protected ResponseEntity<ResponsePacket> get(@PathVariable("id") String id) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get App Code Data Successfully.")
                .responsePacket(appCodeService.get(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/list/{data}")
    protected ResponseEntity<ResponsePacket> list(@PathVariable("data") String data) throws BadRequestException {
        return new ResponseEntity<>(ResponsePacket.builder()
                .errorCode(0)
                .message("Get List Data Successfully.")
                .responsePacket(appCodeService.list(data))
                .build(), HttpStatus.OK);
    }

//    @PutMapping("/activate/{id}")
//    protected ResponseEntity<ResponsePacket> activate(@PathVariable("id") String id) throws BadRequestException {
//        appCodeService.activate(id);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("App Code Activate Successfully.")
//                .build(), HttpStatus.OK);
//    }
//
//    @PutMapping("/inactivate/{id}")
//    protected ResponseEntity<ResponsePacket> inactivate(@PathVariable("id") String id) throws BadRequestException {
//        appCodeService.inactivate(id);
//        return new ResponseEntity<>(ResponsePacket.builder()
//                .errorCode(0)
//                .message("App Code InActivate Successfully.")
//                .build(), HttpStatus.OK);
//    }

}
