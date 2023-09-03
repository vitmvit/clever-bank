package org.example.model.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

public record MoneyAddDto(
        Long bankId,
        Long senderAccountId,
        Long recipientAccountId,
        Date dateTransaction,
        BigDecimal sum) {
}
