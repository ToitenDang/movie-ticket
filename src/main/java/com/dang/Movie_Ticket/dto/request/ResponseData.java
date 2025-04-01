package com.dang.Movie_Ticket.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ResponseData<T>{
    private final int statusCode;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // Constructor for PUT, PATCH, DELETE method
    public ResponseData(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Constructor for POST, GET method
    public ResponseData(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
