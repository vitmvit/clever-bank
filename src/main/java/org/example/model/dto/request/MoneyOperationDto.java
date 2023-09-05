package org.example.model.dto.request;

import org.example.model.constant.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public record MoneyOperationDto(

        TransactionType type,
        Long bankId,
        Long senderAccountId,
        Long recipientAccountId,
        Date dateTransaction,
        BigDecimal sum
) {
}
