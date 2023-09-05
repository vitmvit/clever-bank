package org.example.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.model.constant.TransactionType;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class TransactionUpdateDto {

    private Long id;
    private TransactionType type;
    private Long bankId;
    private Long senderAccountId;
    private Long recipientAccountId;
    private Date dateTransaction;
    private BigDecimal sum;
}
