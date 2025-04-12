package com.dang.Movie_Ticket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    UPLOAD_FILE_FAILED(1009, "Upload file failed!", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1001, "Uncategorized error!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email existed!", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, "Invalid email format!", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1003, "Invalid phone format!", HttpStatus.BAD_REQUEST),
    DOB_INVALID(1004, "Day of birth must be at least {min}!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} character!", HttpStatus.BAD_REQUEST),
    THEATER_STATUS_INVALID(1004, "Only update when theater is in maintenance status!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed!", HttpStatus.NOT_FOUND),
    MOVIE_NOT_EXISTED(1005, "Movie not existed!", HttpStatus.NOT_FOUND),
    SHOWTIME_NOT_EXISTED(1005, "Showtime not existed!", HttpStatus.NOT_FOUND),
    THEATER_NOT_EXISTED(1005, "Theater not existed!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission!", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
