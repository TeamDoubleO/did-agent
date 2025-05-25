package com.doubleo.didagent.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DIDErrorCode implements BaseErrorCode {
    SAMPLE_ERROR(HttpStatus.NOT_FOUND, "DID Agent API Sample Error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
