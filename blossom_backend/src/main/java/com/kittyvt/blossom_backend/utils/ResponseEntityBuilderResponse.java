package com.kittyvt.blossom_backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

public class ResponseEntityBuilderResponse<T> {

    private Object objectResponse;
    private HttpStatus status;
    private String message;
    private String error;
    private MultiValueMap<String, String> headers;

    public ResponseEntityBuilderResponse<T> setHeaders(MultiValueMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public ResponseEntityBuilderResponse<T> setObjectResponse(Object objectResponse) {
        this.objectResponse = objectResponse;
        return this;
    }

    public ResponseEntityBuilderResponse<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseEntityBuilderResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntityBuilderResponse<T> setError(String error) {
        this.error = error;
        return this;
    }

    public ResponseEntity<Object> build() {
        ResponseObject object = new ResponseObject(LocalDateTime.now(), objectResponse, status, message, error);
        return new ResponseEntity<>(object, headers, status);
    }
}