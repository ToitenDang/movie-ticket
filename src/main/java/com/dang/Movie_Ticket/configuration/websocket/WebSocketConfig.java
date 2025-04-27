package com.dang.Movie_Ticket.configuration.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ShowTimeSeatHandler showTimeSeatHandler;

    // Inject từ Spring Context
    public WebSocketConfig(ShowTimeSeatHandler showTimeSeatHandler) {
        this.showTimeSeatHandler = showTimeSeatHandler;
    }

    // Phương thức registerWebSocketHandlers dùng để đăng ký WebSocket handlers với Spring WebSocket
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        // Đăng ký showTimeSeatHandler để xử lý các kết nối WebSocket tại URL /showtime-seat.
        registry.addHandler(showTimeSeatHandler, "/showtime-seat").setAllowedOrigins("*");
        //  Khi client kết nối đến /showtime-seat, Spring sẽ sử dụng ShowTimeSeatHandler để xử lý các sự kiện liên quan
        //  đến WebSocket như gửi hoặc nhận tin nhắn.
    }
}
