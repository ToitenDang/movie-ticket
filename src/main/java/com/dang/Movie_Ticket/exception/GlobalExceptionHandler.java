package com.dang.Movie_Ticket.exception;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseData> handlingRuntimeException(RuntimeException exception){
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        responseData.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        log.error("Runtime error: {}", exception.getMessage(), exception.getCause());
        return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ResponseData> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
//        ResponseData responseData = new ResponseData();
//
//        responseData.setStatusCode(errorCode.getCode());
//        responseData.setMessage(errorCode.getMessage());

        log.error("AppException error: {}", exception.getMessage(), exception.getCause());
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseData.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseData> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        log.error("AccessDeniedException error: {}", exception.getMessage(), exception.getCause());
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseData.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> handlingValidation(MethodArgumentNotValidException exception){
        String enumsKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.KEY_INVALID;

        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumsKey);

            // Lấy ra danh sách các attribute mong muốn
            var constrainValidation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            // Lấy các nội dung là các attributes được truyền vào trong annotation như min hay message.
            attributes = constrainValidation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        } catch (IllegalArgumentException e){}

        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(errorCode.getCode());
        responseData.setMessage(Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(),  attributes)
                        : errorCode.getMessage());
        log.error("Validation error: {}", exception.getMessage(), exception.getCause());
        return ResponseEntity.badRequest().body(responseData);
    }

    // phương thức dùng để replace giá trị được lấy ra từ annotation vào từ khóa trong ErrorCode
    String mapAttribute(String message, Map<String, Object> attributes) {

        // Lấy giá trị trong attributes với tên giống với tên lưu trong biến MIN_ATTRIBUTE
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        // Trả về đoạn message đã được replace giá trị thực trong annotation vào từ khóa được khai báo
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
