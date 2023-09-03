package org.example.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransactionResponseDto {

    private Long id;
    private Long bankId;
    private Long senderAccountId;
    private Long recipientAccountId;
    private Date dateTransaction;
    private BigDecimal sum;
}
