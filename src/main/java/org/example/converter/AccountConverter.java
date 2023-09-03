package org.example.converter;

import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;
import org.example.model.entity.Account;

public class AccountConverter {

    public Account convert(AccountCreateDto source) {
        if (source == null) {
            return null;
        }
        Account target = new Account();
        target.setBankId(source.bankId());
        target.setUserId(source.userId());
        target.setBalance(source.balance());
        return target;
    }

    public Account convert(AccountUpdateDto source) {
        if (source == null) {
            return null;
        }
        Account target = new Account();
        target.setId(source.getId());
        target.setBankId(source.getBankId());
        target.setUserId(source.getUserId());
        target.setBalance(source.getBalance());
        return target;
    }

    public AccountResponseDto convert(Account source) {
        if (source == null) {
            return null;
        }
        AccountResponseDto target = new AccountResponseDto();
        target.setId(source.getId());
        target.setUserId(source.getUserId());
        target.setBankId(source.getBankId());
        target.setBalance(source.getBalance());
        return target;
    }
}
