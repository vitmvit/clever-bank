package org.example.model.dto.request;

import java.math.BigDecimal;

public record AccountCreateDto(

        Long bankId,
        Long userId,
        BigDecimal balance
) {
}
