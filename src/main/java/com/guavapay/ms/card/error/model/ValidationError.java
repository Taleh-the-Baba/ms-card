package com.guavapay.ms.card.error.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Validation error")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

    @ApiModelProperty("Error level")
    private ErrorLevel level;
    @ApiModelProperty("Error property path - payment.amount.currency")
    private String path;
    @ApiModelProperty("Error message")
    private String message;
}