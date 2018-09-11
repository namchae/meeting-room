package me.namchae.meetingroom.exception.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.namchae.meetingroom.exception.code.ErrorCode;

import java.util.List;

// Global Exception Handler Error Response 정의.
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(name = "code", value = "에러 코드", example = "INVALID_REQUEST")
    private final ErrorCode code;

    @ApiModelProperty(name = "message", value = "에러 메시지")
    private final String message;

    @ApiModelProperty(name = "fieldErrors", value = "INVALID_REQUEST 인 경우 오류 Field")
    private final List<FieldError> fieldErrors;

    public ErrorResponse(ErrorCode code) {
        this.code = code;
        this.message = null;
        this.fieldErrors = null;
    }

    public ErrorResponse(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
        this.fieldErrors = null;
    }

    @Getter
    @Builder
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;
    }

}
