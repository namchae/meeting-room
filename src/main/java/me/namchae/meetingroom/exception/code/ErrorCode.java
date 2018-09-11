package com.kakaoix.store.exception.code;

// Exception Handler 에러코드 정의.
public enum ErrorCode {

    // ------------------------------------------------------------------------
    //  4xx
    // ------------------------------------------------------------------------
    INVALID_REQUEST,
    REQUEST_PARAMETER_NOT_VALID,
    MEMBER_NOT_FOUND,
    MEMBER_DUPLICATE,
    MEMBER_WRONG_PASSWORD,
    MEMBER_FAIL_COUNT_OVER,

    ORDER_NOT_FOUND,
    ORDER_ALREADY_CANCEL,

    PRODUCT_DUPLICATE,
    PRODUCT_STOCK_OVERFLOW,

    // ------------------------------------------------------------------------
    //  5xx
    // ------------------------------------------------------------------------
    INTERNAL_SERVER_ERROR,


}
