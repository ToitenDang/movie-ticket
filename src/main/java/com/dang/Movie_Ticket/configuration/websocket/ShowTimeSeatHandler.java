package com.dang.Movie_Ticket.configuration.websocket;

import com.dang.Movie_Ticket.dto.request.IntrospectRequest;
import com.dang.Movie_Ticket.dto.request.SeatUpdateRequest;
import com.dang.Movie_Ticket.service.AuthenticationService;
import com.dang.Movie_Ticket.service.ShowTimeSeatService;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.*;

@Component
public class ShowTimeSeatHandler extends TextWebSocketHandler {
    @Autowired
    private  ShowTimeSeatService showTimeSeatService;
    @Autowired
    private AuthenticationService authenticationService;
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final ConcurrentHashMap<String, ScheduledFuture<?>> seatResetTasks = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = session.getHandshakeHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Loại bỏ "Bearer " để lấy token thật sự
            var response = authenticationService.introspect(IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) {
                session.close(); // Nếu token không hợp lệ, từ chối kết nối WebSocket
            }
        } else {
            session.close(); // Nếu không có token, từ chối kết nối
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SeatUpdateRequest seatRequest = objectMapper.readValue(message.getPayload(), SeatUpdateRequest.class);

        this.showTimeSeatService.changeShowTimeSeatStatus(seatRequest.getShowtimeId(), seatRequest.getSeatId(), seatRequest.getStatus());
        if (SeatStatus.RESERVED.equals(seatRequest.getStatus())) {
            scheduleSeatReset(seatRequest.getShowtimeId(), seatRequest.getSeatId());
        } else if (SeatStatus.BOOKED.equals(seatRequest.getStatus())) {
            cancelSeatReset(seatRequest.getSeatId());
        }


        broadcastUpdate(message.getPayload());
    }

    private void scheduleSeatReset(String showtimeId, String seatId) {
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            if (showTimeSeatService.isSeatStillReserve(showtimeId, seatId)) {
                showTimeSeatService.changeShowTimeSeatStatus(showtimeId, seatId, SeatStatus.EMPTY);
                try {
                    broadcastUpdate("{ \"showtimeId\": " + showtimeId + ", \"seatId\": " + seatId + ", \"status\": \"EMPTY\" }");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            seatResetTasks.remove(seatId);
        }, 5, TimeUnit.MINUTES);

        seatResetTasks.put(seatId, task);
    }

    private void cancelSeatReset(String seatId) {
        ScheduledFuture<?> task = seatResetTasks.remove(seatId);
        if (task != null) {
            task.cancel(false);
        }
    }


    private void broadcastUpdate(String payload) throws Exception {
        for (WebSocketSession session : sessions.values()) {
            session.sendMessage(new TextMessage(payload));
        }
    }
}