package org.example.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Receipt {

    private Long number;
    private String typeTransaction;
    private String senderBankName;
    private String recipientBankName;
    private Long senderAccountId;
    private Long recipientAccountId;
    private BigDecimal sum;
}
