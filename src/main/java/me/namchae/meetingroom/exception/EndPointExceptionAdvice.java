package me.namchae.meetingroom.exception;


import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import me.namchae.meetingroom.booking.exception.InValidRangeException;
import me.namchae.meetingroom.booking.exception.InValidThirtyMinutesException;
import me.namchae.meetingroom.exception.code.ErrorCode;
import me.namchae.meetingroom.exception.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

// Exception 발생에 따른 Handler 정의.
@Slf4j
@ControllerAdvice
public class EndPointExceptionAdvice {


    // 시작시간 종료시간 Exception Handler
    @ExceptionHandler({InValidRangeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onInValidRangeException(InValidRangeException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_RANGE_TIME, e.getMessage());
    }

    // 요청시간 30분단위 Exception Handler
    @ExceptionHandler({InValidThirtyMinutesException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onInValidThirtyMinutesException(InValidThirtyMinutesException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_THIRTY_TIME, e.getMessage());
    }

    @ExceptionHandler({DuplicateBookingException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onProductNotFoundException(DuplicateBookingException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.DUPLICATE_BOOKING, String.format("요청한 예약건은 이미 존재합니다. %s", e.getMessage()));
    }

    // 공통 Exception Handler
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onError(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onHttpMessageNotReadableException(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onBindException(BindException e) {
        log.error(e.getMessage());

        return buildError(e.getBindingResult());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());

        return buildError(e.getBindingResult());
    }

    private ErrorResponse buildError(BindingResult bindingResult) {
        return ErrorResponse.builder()
                .code(ErrorCode.REQUEST_PARAMETER_NOT_VALID)
                .message("Request parameter is not valid.")
                .fieldErrors(
                        bindingResult
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> ErrorResponse.FieldError.builder()
                                        .field(fieldError.getField())
                                        .value(String.valueOf(fieldError.getRejectedValue()))
                                        .reason(fieldError.getDefaultMessage())
                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }
}
