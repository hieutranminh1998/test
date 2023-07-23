package com.example.health.controller;

import com.example.health.configuration.api.ApiController;
import com.example.health.dto.StepDto;
import com.example.health.form.StepForm;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.JsonUtil;
import com.example.health.ulti.ResponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StepController extends ApiController {

    @Autowired
    StepService stepService;

    @ApiOperation(value = "Add step")
    @PostMapping("/step")
    public ResponseEntity<ResponseData> addStep(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestBody @Valid StepForm form, BindingResult bindingResult
            ) {

        log.info(getMethodName(), JsonUtil.toString(form));
        ResponseData responseData;
        if (bindingResult.hasErrors()){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        try {
            StepDto data = stepService.addStep(form);
            responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, data, AppConstant.SUCCESS);
            log.info(getMethodName(), JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null, e.getMessage());
            log.info(getMethodName(), e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

    }

//    @ApiOperation(value = "Get Rank")
//    @GetMapping("/step/rank")
//    public ResponseEntity<ReponseData<StepRankDto>> getRank(
//            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
//            @RequestHeader(value = "messageId", required = true) String messageId
//    ){
//
//    }


}
