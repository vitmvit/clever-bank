package org.example.service;

import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;

public interface AccountService {

    AccountResponseDto findById(Long id);

    AccountResponseDto create(AccountCreateDto account);

    AccountResponseDto update(AccountUpdateDto account);

    void delete(Long id);

    void moneyAdd(MoneyOperationDto transaction);

    void moneyRemove(MoneyOperationDto transaction);

    void moneyTransfer(MoneyOperationDto transaction);
}
