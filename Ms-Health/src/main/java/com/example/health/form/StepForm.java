package com.example.health.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class StepForm {
    @NotNull(message = "Step is required")
    @Min(value = 1, message = "Invalid step")
    @Max(value = 90000, message = "Invalid step")
    int step;

    @NotEmpty(message = "CustomerId is required")
    String customerId;

    @DecimalMin(value = "0.0", message = "Invalid distance")
    @NotNull(message = "Distance is required")
    BigDecimal distance;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    Date date;
}
