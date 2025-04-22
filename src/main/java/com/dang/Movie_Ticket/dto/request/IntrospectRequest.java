package com.dang.Movie_Ticket.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IntrospectRequest {
    private String token;
}
