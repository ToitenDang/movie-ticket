package com.dang.Movie_Ticket.dto.request;

public class ResponseError extends ResponseData{
    public ResponseError(int statusCode, String message) {
        super(statusCode, message);
    }
}
