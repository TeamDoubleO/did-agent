package com.doubleo.didagent.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DIDErrorCode implements BaseErrorCode {
    MALFORMED_PEER_DID(HttpStatus.NOT_FOUND, "잘못된 형식의 DID 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
