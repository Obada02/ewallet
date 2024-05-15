package com.github.ewallet.dto.response;

import com.github.ewallet.model.RoleType;
import lombok.Data;

/**
 * Data Transfer Object for Role response
 */
@Data
public class RoleResponse {

    private Long id;
    private RoleType type;
}
