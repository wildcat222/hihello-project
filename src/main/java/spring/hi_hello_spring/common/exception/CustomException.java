package spring.hi_hello_spring.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    ErrorCodeType errorCode;
    String message;

    public CustomException(ErrorCodeType errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCodeType errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;  // 커스텀 메시지 사용
    }

}
