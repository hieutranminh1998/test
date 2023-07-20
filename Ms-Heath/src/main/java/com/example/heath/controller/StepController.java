package com.example.heath.controller;

import com.example.heath.api.impl.AddStep;
import com.example.heath.dto.StepDto;
import com.example.heath.input.StepInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StepController {


    @ApiOperation(value = "Add step")
    @PostMapping("/heath/step")
    public ResponseEntity<StepDto> addStep(
            @ApiParam(required = true, value = "UUID v4", example = "a56d851d-86ab-4d67-8a49-10ee639a206d")
            @RequestHeader(value = "messageId", required = true) String messageId,
            @RequestBody StepInput stepInput
            ) {
        return new AddStep().execute(messageId, null, stepInput, null);
    }


}
