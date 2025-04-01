package com.dang.Movie_Ticket.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    private boolean authenticated;
}
