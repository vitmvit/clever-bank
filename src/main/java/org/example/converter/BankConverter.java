package org.example.converter;

import org.example.model.dto.request.BankCreateDto;
import org.example.model.dto.response.BankResponseDto;
import org.example.model.dto.response.BankUpdateDto;
import org.example.model.entity.Bank;

public class BankConverter {

    public Bank convert(BankCreateDto source) {
        if (source == null) {
            return null;
        }
        Bank target = new Bank();
        target.setName(source.name());
        return target;
    }

    public Bank convert(BankUpdateDto source) {
        if (source == null) {
            return null;
        }
        Bank target = new Bank();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    public BankResponseDto convert(Bank source) {
        if (source == null) {
            return null;
        }
        BankResponseDto target = new BankResponseDto();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
