package com.example.heath.controller;

import com.example.heath.configuration.api.ApiController;
import com.example.heath.dto.StepDto;
import com.example.heath.input.StepInput;
import com.example.heath.service.StepService;
import com.example.heath.ulti.AppConstant;
import com.example.heath.ulti.JsonUtil;
import com.example.heath.ulti.ReponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StepController extends ApiController {

    @Autowired
    StepService stepService;

    @ApiOperation(value = "Add step")
    @PostMapping("/heath/step")
    public ResponseEntity<ReponseData<StepDto>> addStep(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestBody StepInput stepInput
            ) {
        log.info(JsonUtil.toString(stepInput));
        ReponseData<StepDto> responseData;
        try {
            StepDto stepDto = stepService.addStep(stepInput);
            responseData = new ReponseData<>(AppConstant.HTTP_STATUS.SUCCESS, stepDto, AppConstant.SUCCESS);
            log.info(JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ReponseData<>(AppConstant.HTTP_STATUS.BAD_REQUEST, null, e.getMessage());
            log.info("", e);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }

    }


}
