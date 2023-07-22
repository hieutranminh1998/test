package com.example.health.controller;

import com.example.health.configuration.api.ApiController;
import com.example.health.dto.StepDto;
import com.example.health.input.StepInput;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.JsonUtil;
import com.example.health.ulti.ReponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StepController extends ApiController {

    @Autowired
    StepService stepService;

    @ApiOperation(value = "Add step")
    @PostMapping("/health/step")
    public ResponseEntity<ReponseData<StepDto>> addStep(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestBody @Valid StepInput stepInput, BindingResult bindingResult
            ) {
        log.info(JsonUtil.toString(stepInput));
        ReponseData<StepDto> responseData;
        if (bindingResult.hasErrors()){
            responseData = new ReponseData<>(AppConstant.HTTP_STATUS.BAD_REQUEST, null,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        try {
            StepDto stepDto = stepService.addStep(stepInput);
            responseData = new ReponseData<>(AppConstant.HTTP_STATUS.SUCCESS, stepDto, AppConstant.SUCCESS);
            log.info(JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ReponseData<>(AppConstant.HTTP_STATUS.BAD_REQUEST, null, e.getMessage());
            log.info("", e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

    }


}
