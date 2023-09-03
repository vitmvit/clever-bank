package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;
import org.example.service.AccountService;
import org.example.service.AccountServiceTest;
import org.example.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

class AccountServiceTestImpl implements AccountServiceTest {

    private final AccountService accountService = new AccountServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();

    @Test
    public void findByIdPositive() {
        AccountResponseDto accountResponseDto = accountService.create(getAccount());
        Assertions.assertNotNull(accountService.findById(accountResponseDto.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            accountService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void createPositive() {
        AccountCreateDto accountCreateDto = getAccount();
        AccountResponseDto accountResponseDto = accountService.create(accountCreateDto);
        Assertions.assertNotNull(accountResponseDto.getId());
        Assertions.assertEquals(accountCreateDto.bankId(), accountResponseDto.getBankId());
    }

    @Test
    public void createNegative() {
        AccountCreateDto accountCreateDto = new AccountCreateDto(null, null, new BigDecimal(3));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            accountService.create(accountCreateDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void updatePositive() {
        AccountCreateDto accountCreateDto = getAccount();

        AccountResponseDto saved = accountService.create(accountCreateDto);

        AccountUpdateDto account = new AccountUpdateDto();
        account.setId(saved.getId());
        account.setBankId(saved.getBankId());
        account.setUserId(saved.getUserId());
        account.setBalance(new BigDecimal(Long.MAX_VALUE));

        AccountResponseDto updated = accountService.update(account);
        Assertions.assertNotEquals(saved.getBalance(), updated.getBalance());
    }

    @Test
    public void updateNegative() {
        AccountUpdateDto account = new AccountUpdateDto();
        account.setId(null);
        account.setBankId(null);
        account.setUserId(null);
        account.setBalance(null);

        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            accountService.update(account);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void delete() {
        AccountResponseDto saved = accountService.create(getAccount());
        accountService.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            accountService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void moneyTransferPositive() {
        AccountCreateDto accountFrom = getAccount();
        AccountResponseDto accountFromSaved = accountService.create(accountFrom);

        AccountCreateDto accountTo = getAccount();
        AccountResponseDto accountToSaved = accountService.create(accountTo);

        MoneyOperationDto transaction = new MoneyOperationDto(1L, accountFromSaved.getId(), accountToSaved.getId(), new Date(1212121212121L), BigDecimal.valueOf(100));
        transactionService.create(transaction);

        accountService.moneyTransfer(transaction);

        Assertions.assertEquals(accountFrom.balance().subtract(new BigDecimal(100)), accountService.findById(accountFromSaved.getId()).getBalance());
        Assertions.assertEquals(accountTo.balance().add(new BigDecimal(100)), accountService.findById(accountToSaved.getId()).getBalance());

    }

    @Test
    public void moneyTransferNegative() {
        AccountCreateDto accountFrom = getAccount();
        AccountResponseDto accountFromSaved = accountService.create(accountFrom);

        AccountCreateDto accountTo = getAccount();
        AccountResponseDto accountToSaved = accountService.create(accountTo);

        MoneyOperationDto transaction = new MoneyOperationDto(1L, accountFromSaved.getId(), accountToSaved.getId(), new Date(1212121212121L), accountFromSaved.getBalance().add(new BigDecimal(100)));

        transactionService.create(transaction);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            accountService.moneyTransfer(transaction);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Недостаточно средств на счете"));
    }

    private AccountCreateDto getAccount() {
        return new AccountCreateDto(1l, 2l, new BigDecimal(777));
    }
}