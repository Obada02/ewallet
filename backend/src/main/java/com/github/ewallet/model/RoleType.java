package com.github.ewallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private String label;
}
