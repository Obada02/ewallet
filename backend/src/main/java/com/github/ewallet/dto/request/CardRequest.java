package com.github.ewallet.dto.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardRequest {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Credentials are required")
    @Size(min = 1, max = 50, message = "Credentials must be between 1 and 50 characters")
    private String credentials;

    @NotNull(message = "Expiry date is required")
    private LocalDateTime expiryDate;
}
