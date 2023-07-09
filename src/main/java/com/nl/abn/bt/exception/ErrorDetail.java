package com.nl.abn.bt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorDetail {

    INTERNAL_SERVER_ERROR("LMFS500", "Internal server error please try later", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_RESOURCE_NOT_FOUND_EXCEPTION("LMFS404", "Resource Not Available", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_BAD_REQUEST_EXCEPTION("LMFS500", "Bad Request error from service", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_AUTHENTICATION_SERVICE_EXCEPTION("LMFS500", "Details is not found", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_USER_EXITS_SERVICE_EXCEPTION("LMFS500", "User is already exists", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_FORBIDDEN_EXCEPTION("LMFS500", "The user is not permitted to perform the requested operation", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_DETAILS_NOT_FOUND("LMFS500", "Country details is not found", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    ErrorDetail(String id, String msg, HttpStatus status) {
        this.errorCode = id;
        this.errorMessage = msg;
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
