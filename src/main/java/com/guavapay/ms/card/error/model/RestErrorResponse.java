package com.guavapay.ms.card.error.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Error Rest response
 */
@ApiModel("Error response from Rest service")
@Data
@ToString
@NoArgsConstructor
public class RestErrorResponse {

    @ApiModelProperty("Error unique ID")
    private String uuid;

    @ApiModelProperty("Error code - NOT_FOUND, INTERNAL_SERVER_ERROR etc")
    private String code;

    @ApiModelProperty("Error message")
    private String message;

    @ApiModelProperty("Validation errors")
    private List<ValidationError> checks;

    public RestErrorResponse(String uuid, String code, String message) {
        this.uuid = uuid;
        this.code = code;
        this.message = message;
    }

    public RestErrorResponse(String uuid, HttpStatus httpStatus, List<ValidationError> checks) {
        this.uuid = uuid;
        this.code = httpStatus.name();
        this.message = httpStatus.getReasonPhrase();
        this.checks = checks;
    }
}

