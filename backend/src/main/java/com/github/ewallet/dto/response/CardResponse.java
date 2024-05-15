package com.github.ewallet.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardResponse {

    private Long id;
    private Long userId;
    private String credentials;
    private LocalDateTime expiryDate;
}
