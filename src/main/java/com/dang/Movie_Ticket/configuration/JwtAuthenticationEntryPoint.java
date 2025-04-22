package com.dang.Movie_Ticket.configuration;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Lấy ra errorCode tương ứng lỗi
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        // Lấy ra statusCode khai báo trong errorCode
        response.setStatus(errorCode.getStatusCode().value());

        // Định dạng kiểu trả về là Json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Build đối tượng apiResponse trả về.
        ResponseData<?> apiResponse = ResponseData.builder()
                .statusCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        // Tạo đối tượng objectMapper để map dữ liệu từ apiResponse sang object
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

        // Gửi respone về cho client
        response.flushBuffer();
        System.out.println("Authentication error: " + authException.getMessage());
    }
}
// -> Vì exception này xảy ra trên tầng filter nên phải config bắt nó trong lúc xác thực người dùng.
