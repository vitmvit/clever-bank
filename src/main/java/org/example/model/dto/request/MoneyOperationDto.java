package org.example.model.dto.request;

import java.math.BigDecimal;
import java.util.Date;

public record MoneyOperationDto(

        Long bankId,
        Long senderAccountId,
        Long recipientAccountId,
        Date dateTransaction,
        BigDecimal sum
) {
}
