package org.example.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.parent.BaseModel;

import java.math.BigDecimal;

@Getter
@Setter
public class Account extends BaseModel {

    private Long bankId;
    private Long userId;
    private BigDecimal balance;
}
