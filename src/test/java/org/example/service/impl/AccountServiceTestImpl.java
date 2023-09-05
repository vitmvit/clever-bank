package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;
import org.example.service.AccountService;
import org.example.service.AccountServiceTest;
import org.example.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceTestImpl implements AccountServiceTest {

    private final AccountService accountService = Mockito.mock(AccountServiceImpl.class);
    private final TransactionService transactionService = Mockito.mock(TransactionServiceImpl.class);

    @Test
    public void findByIdPositiveTest() {
        AccountResponseDto target = getAccountResponseDto();
        when(accountService.findById(1L)).thenReturn(target);
        AccountResponseDto result = accountService.findById(target.getId());
        assertEquals(target.getId(), result.getId());
        assertEquals(target.getBalance(), result.getBalance());

        AccountResponseDto accountResponseDto = accountService.create(getAccountCreateDto());
        Assertions.assertNotNull(accountService.findById(accountResponseDto.getId()));
    }

    @Test
    public void findByIdNegativeTest() {
        when(accountService.findById(anyLong())).thenThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE));
        var exception = Assertions.assertThrows(Exception.class, () -> {
            accountService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createPositiveTest() {
        AccountResponseDto target = getAccountResponseDto();
        AccountCreateDto accountCreateDto = getAccountCreateDto();
        when(accountService.create(accountCreateDto)).thenReturn(target);
        AccountResponseDto actualResponse = accountService.create(accountCreateDto);
        Assertions.assertNotNull(actualResponse.getId());
        Assertions.assertEquals(accountCreateDto.balance(), actualResponse.getBalance());
    }

    @Test
    public void createNegativeTest() {
        AccountCreateDto target = new AccountCreateDto(null, null, null);
        doThrow(RequestException.class).when(accountService).create(target);
        var exception = Assertions.assertThrows(Exception.class,
                () -> accountService.create(target));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositiveTest() {
        AccountCreateDto bankCreateDto = getAccountCreateDto();
        AccountResponseDto saved = getAccountResponseDto();
        when(accountService.create(bankCreateDto)).thenReturn(saved);
        AccountUpdateDto user = getAccountUpdateDto();
        AccountResponseDto updated = new AccountResponseDto();
        updated.setId(saved.getId());
        updated.setBankId(1L);
        updated.setUserId(1L);
        updated.setBalance(new BigDecimal(700));
        when(accountService.update(user)).thenReturn(updated);
        Assertions.assertNotEquals(saved.getBalance(), updated.getBalance());
    }

    @Test
    public void updateNegativeTest() {
        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setId(null);
        accountUpdateDto.setBankId(null);
        accountUpdateDto.setUserId(null);
        accountUpdateDto.setBalance(null);
        when(accountService.update(accountUpdateDto)).thenThrow(new RequestException(REQUEST_EXCEPTION_MESSAGE));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            accountService.update(accountUpdateDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void deleteTest() {
        AccountCreateDto bankCreateDto = getAccountCreateDto();
        AccountResponseDto saved = getAccountResponseDto();
        doReturn(saved).when(accountService).create(bankCreateDto);
        doNothing().when(accountService).delete(saved.getId());
        doThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE)).when(accountService).findById(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            accountService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void moneyTransferPositiveTest() {

    }

    @Test
    public void moneyTransferNegativeTest() {

    }

    private AccountResponseDto getAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(1L);
        accountResponseDto.setBankId(1L);
        accountResponseDto.setUserId(1L);
        accountResponseDto.setBalance(new BigDecimal(500));
        return accountResponseDto;
    }

    private AccountCreateDto getAccountCreateDto() {
        return new AccountCreateDto(1L, 1L, new BigDecimal(500));
    }

    private AccountUpdateDto getAccountUpdateDto() {
        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setId(1L);
        accountUpdateDto.setBankId(1L);
        accountUpdateDto.setUserId(1L);
        accountUpdateDto.setBalance(new BigDecimal(500));
        return accountUpdateDto;
    }
}