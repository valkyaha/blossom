package com.kittyvt.blossom_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseObject {

    private LocalDateTime timestamp;
    private Object objectResponse;
    private HttpStatus status;
    private String message;
    private String error;

}