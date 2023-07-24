package com.example.health.controller;

import com.example.health.configuration.api.ApiController;
import com.example.health.dto.StepDto;
import com.example.health.dto.StepRankDto;
import com.example.health.dto.TotalStepMonthDto;
import com.example.health.dto.TotalStepWeekDto;
import com.example.health.form.StepForm;
import com.example.health.service.StepCacheService;
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
import java.util.List;

@RestController
public class StepController extends ApiController {

    @Autowired
    StepService stepService;

    @Autowired
    StepCacheService stepCacheService;

    @ApiOperation(value = "Add step")
    @PostMapping("/step")
    public ResponseEntity<ResponseData> addStep(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestBody @Valid StepForm form, BindingResult bindingResult
            ) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(methodName + JsonUtil.toString(form));
        ResponseData responseData;
        if (bindingResult.hasErrors()){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        try {
            StepDto data = stepService.addStep(form);
            responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, data, AppConstant.SUCCESS);
            log.info(methodName + JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null, e.getMessage());
            log.info(methodName + e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Get Rank month")
    @GetMapping("/step/rank")
    public ResponseEntity<ResponseData> getRank(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(methodName);
        ResponseData responseData;
        try {
            List<StepRankDto> data = stepCacheService.getListRank(offset, limit);
            responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, data, AppConstant.SUCCESS);
            log.info(methodName + JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null, e.getMessage());
            log.info(methodName + e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get step in this week")
    @GetMapping("/step/{customerId}/this-week")
    public ResponseEntity<ResponseData> getStepInThisWeek(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @PathVariable String customerId
    ){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(methodName + JsonUtil.toString(customerId));
        ResponseData responseData;
        try {
            TotalStepWeekDto data = stepService.getStepInThisWeek(customerId);
            responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, data, AppConstant.SUCCESS);
            log.info(methodName + JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null, e.getMessage());
            log.info(methodName + e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Get step in this month")
    @GetMapping("/step/{customerId}/this-month")
    public ResponseEntity<ResponseData> getStepInThisMonth(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @PathVariable String customerId
    ){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(methodName + JsonUtil.toString(customerId));
        ResponseData responseData;
        try {
            TotalStepMonthDto data = stepService.getStepInThisMonth(customerId);
            responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, data, AppConstant.SUCCESS);
            log.info(methodName + JsonUtil.toString(responseData));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData = new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST, null, e.getMessage());
            log.info(methodName + e);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


}
