package com.example.health.controller;

import com.example.health.dto.StepDto;
import com.example.health.form.StepForm;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.ResponseData;
import org.apache.kafka.common.errors.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StepControllerTest {

    @Mock
    private StepService stepService;

    @InjectMocks
    StepController stepController;

    @Mock
    BindingResult bindingResult;

    @Test
    void addStep() {
        String messageSn = "";
        StepForm form = new StepForm();
        StepDto stepDto = new StepDto();
        String messageError = "";
        String objectName = "";
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(objectName, messageError));
        Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
        ResponseEntity<ResponseData> response
                = new ResponseEntity<>(
                        new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST,
                                null, messageError), HttpStatus.BAD_REQUEST);

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals(stepController.addStep(messageSn, form, bindingResult), response);


        ResponseData responseData = new ResponseData(AppConstant.STATUS_CODE.SUCCESS, stepDto, AppConstant.SUCCESS);
        response = new ResponseEntity<>(responseData, HttpStatus.OK);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(stepService.addStep(form)).thenReturn(stepDto);
        assertEquals(stepController.addStep(messageSn, form, bindingResult), response);

        Mockito.when(stepService.addStep(form)).thenThrow(ApiException.class);
        response
                = new ResponseEntity<>(
                new ResponseData(AppConstant.STATUS_CODE.BAD_REQUEST,
                        null, null), HttpStatus.BAD_REQUEST);
        assertEquals(stepController.addStep(messageSn, form, bindingResult), response);
        }


}
