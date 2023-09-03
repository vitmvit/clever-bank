package org.example.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponseDto {

    private Long id;
    private Long bankId;
    private Long userId;
    private BigDecimal balance;
}
