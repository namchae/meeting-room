package com.kakaoix.store.exception;

import com.kakaoix.store.exception.code.ErrorCode;
import com.kakaoix.store.exception.domain.ErrorResponse;
import com.kakaoix.store.member.exception.InvalidPasswordException;
import com.kakaoix.store.member.exception.MemberDuplicateException;
import com.kakaoix.store.member.exception.MemberNotFoundException;
import com.kakaoix.store.member.exception.PasswordFailedOverException;
import com.kakaoix.store.order.exception.OrderAlreadyCancelException;
import com.kakaoix.store.order.exception.OrderNotFoundException;
import com.kakaoix.store.product.exception.NotEnoughStackOverException;
import com.kakaoix.store.product.exception.ProductDuplicateException;
import com.kakaoix.store.product.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
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

    // 회원관련 Exception Handler
    @ExceptionHandler({MemberNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse onMemberNotFoundException(MemberNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND, String.format("요청한 회원[%s]을 찾지 못했습니다.", e.getEmail()));
    }

    @ExceptionHandler({MemberDuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onMemberDuplicateException(MemberDuplicateException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.MEMBER_DUPLICATE, String.format("요청한 회원[%s]은 이미 존재합니다.", e.getEmail()));
    }

    @ExceptionHandler({InvalidPasswordException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onMemberDuplicateException(InvalidPasswordException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.MEMBER_WRONG_PASSWORD, "요청한 비밀번호가 올바르지않습니다.");
    }

    @ExceptionHandler({PasswordFailedOverException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onPasswordFailedOverException(PasswordFailedOverException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.MEMBER_FAIL_COUNT_OVER, String.format("비밀번호 실패[%d회] 초과하셨습니다.", e.getFailedCount()));
    }

    // 주문관련 Exception Handler
    @ExceptionHandler({OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse onOrderNotFoundException(OrderNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.ORDER_NOT_FOUND, String.format("요청한 주문번호[%d]를 찾을 수 없습니다.", e.getOrderId()));
    }


    // 주문관련 Exception Handler
    @ExceptionHandler({OrderAlreadyCancelException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onOrderAlreadyCancelException(OrderAlreadyCancelException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.ORDER_ALREADY_CANCEL, String.format("해당 주문번호[%d]는 이미 취소되었습니다.", e.getOrderId()));
    }


    // 상품관련 Exception Handler
    @ExceptionHandler({ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse onProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.ORDER_NOT_FOUND, String.format("요청한 상품번호[%d]를 찾을 수 없습니다.", e.getProductId()));
    }

    @ExceptionHandler({ProductDuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse onProductNotFoundException(ProductDuplicateException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.PRODUCT_DUPLICATE, String.format("요청한 상품명[%s]은 이미 존재합니다.", e.getProductName()));
    }

    @ExceptionHandler({NotEnoughStackOverException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onNotEnoughStackOverException(NotEnoughStackOverException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.PRODUCT_STOCK_OVERFLOW, String.format("요청한 상품번호[%d]의 재고수량이 부족합니다.", e.getProductId()));
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
