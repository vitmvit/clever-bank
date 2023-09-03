package org.example.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.constant.TransactionType;
import org.example.model.entity.parent.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Transaction extends BaseModel {

    private TransactionType type;
    private Long bankId;
    private Long senderAccountId;
    private Long recipientAccountId;
    private Date dateTransaction;
    private BigDecimal sum;
}
