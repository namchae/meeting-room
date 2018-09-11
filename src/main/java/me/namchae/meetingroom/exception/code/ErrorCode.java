package me.namchae.meetingroom.exception.code;

// Exception Handler 에러코드 정의.
public enum ErrorCode {

    // ------------------------------------------------------------------------
    //  4xx
    // ------------------------------------------------------------------------
    INVALID_REQUEST,
    REQUEST_PARAMETER_NOT_VALID,

    //TODO: 삭제.
    INVALID_RANGE_TIME,
    INVALID_THIRTY_TIME,

    DUPLICATE_BOOKING,


    // ------------------------------------------------------------------------
    //  5xx
    // ------------------------------------------------------------------------
    INTERNAL_SERVER_ERROR,


}
