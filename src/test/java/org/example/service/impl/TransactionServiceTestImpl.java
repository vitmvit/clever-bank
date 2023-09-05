package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.constant.TransactionType;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;
import org.example.service.TransactionService;
import org.example.service.TransactionServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class TransactionServiceTestImpl implements TransactionServiceTest {

    private final TransactionService transactionService = Mockito.mock(TransactionServiceImpl.class);

    @Test
    public void findByIdPositiveTest() {
        TransactionResponseDto target = getTransactionResponseDto();
        when(transactionService.findById(1L)).thenReturn(target);
        TransactionResponseDto result = transactionService.findById(target.getId());
        assertEquals(target.getSum(), result.getSum());
    }

    @Test
    public void findByIdNegativeTest() {
        when(transactionService.findById(anyLong())).thenThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE));
        var exception = Assertions.assertThrows(Exception.class, () -> {
            transactionService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void getAllTransactionsByUserIdPositiveTest() {
        List<TransactionResponseDto> list = List.of(
                getTransactionResponseDto(),
                getTransactionResponseDto(),
                getTransactionResponseDto()
        );
        when(transactionService.getAllTransactionsByUserId(list.get(0).getId())).thenReturn(list);
    }

    @Test
    public void getAllTransactionsByUserIdNegativeTest() {
        Long id = Long.MAX_VALUE;
        List<TransactionResponseDto> list = new ArrayList<>();
        when(transactionService.getAllTransactionsByUserId(id)).thenReturn(list);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void getAllTransactionsByDatePositiveTest() {
        List<TransactionResponseDto> list = List.of(
                getTransactionResponseDto(),
                getTransactionResponseDto(),
                getTransactionResponseDto()
        );
        when(transactionService.getAllTransactionsByUserId(list.get(0).getDateTransaction().getTime())).thenReturn(list);
    }

    @Test
    public void getAllTransactionsByDateNegativeTest() {
        Date date = new java.sql.Date(1212121212121L);
        List<TransactionResponseDto> list = new ArrayList<>();
        when(transactionService.getAllTransactionsByDate(date)).thenReturn(list);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void createPositiveTest() {
        TransactionResponseDto target = getTransactionResponseDto();
        MoneyOperationDto transactionCreateDto = getTransactionCreateDto();
        when(transactionService.create(transactionCreateDto)).thenReturn(target);
        TransactionResponseDto actualResponse = transactionService.create(transactionCreateDto);
        Assertions.assertEquals(transactionCreateDto.bankId(), actualResponse.getBankId());
        Assertions.assertEquals(transactionCreateDto.senderAccountId(), actualResponse.getSenderAccountId());
        Assertions.assertEquals(transactionCreateDto.recipientAccountId(), actualResponse.getRecipientAccountId());
        Assertions.assertEquals(transactionCreateDto.dateTransaction(), actualResponse.getDateTransaction());
        Assertions.assertEquals(transactionCreateDto.sum(), actualResponse.getSum());
    }

    @Test
    public void createNegativeTest() {
        MoneyOperationDto target = new MoneyOperationDto(null, null, null, null, null, null);
        doThrow(RequestException.class).when(transactionService).create(target);
        var exception = Assertions.assertThrows(Exception.class,
                () -> transactionService.create(target));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositiveTest() {
        MoneyOperationDto accountCreateDto = getTransactionCreateDto();
        TransactionResponseDto saved = getTransactionResponseDto();
        when(transactionService.create(accountCreateDto)).thenReturn(saved);
        TransactionUpdateDto user = getTransactionUpdateDto();
        TransactionResponseDto updated = new TransactionResponseDto();
        updated.setId(saved.getId());
        updated.setBankId(saved.getBankId());
        updated.setSenderAccountId(saved.getSenderAccountId());
        updated.setRecipientAccountId(saved.getRecipientAccountId());
        updated.setDateTransaction(saved.getDateTransaction());
        updated.setSum(new BigDecimal(800));
        when(transactionService.update(user)).thenReturn(updated);
        Assertions.assertNotEquals(saved.getSum(), updated.getSum());
    }

    @Test
    public void updateNegativeTest() {
        TransactionUpdateDto transactionUpdateDto = new TransactionUpdateDto();
        transactionUpdateDto.setId(null);
        transactionUpdateDto.setBankId(null);
        transactionUpdateDto.setSenderAccountId(null);
        transactionUpdateDto.setRecipientAccountId(null);
        transactionUpdateDto.setDateTransaction(null);
        transactionUpdateDto.setSum(null);
        when(transactionService.update(transactionUpdateDto)).thenThrow(new RequestException(REQUEST_EXCEPTION_MESSAGE));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            transactionService.update(transactionUpdateDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void deleteTest() {
        MoneyOperationDto moneyOperationDto = getTransactionCreateDto();
        TransactionResponseDto saved = getTransactionResponseDto();
        doReturn(saved).when(transactionService).create(moneyOperationDto);
        doNothing().when(transactionService).delete(saved.getId());
        doThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE)).when(transactionService).findById(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    private TransactionResponseDto getTransactionResponseDto() {
        TransactionResponseDto transaction = new TransactionResponseDto();
        transaction.setId(1L);
        transaction.setType(TransactionType.A);
        transaction.setBankId(1L);
        transaction.setSenderAccountId(2L);
        transaction.setRecipientAccountId(3L);
        transaction.setDateTransaction(new Date(1212121212121L));
        transaction.setSum(new BigDecimal(200));
        return transaction;
    }

    private MoneyOperationDto getTransactionCreateDto() {
        return new MoneyOperationDto(TransactionType.A, 1L, 2L, 3L, new Date(1212121212121L), new BigDecimal(200));
    }

    private TransactionUpdateDto getTransactionUpdateDto() {
        TransactionUpdateDto transaction = new TransactionUpdateDto();
        transaction.setType(TransactionType.A);
        transaction.setBankId(1L);
        transaction.setSenderAccountId(2L);
        transaction.setRecipientAccountId(3L);
        transaction.setDateTransaction(new java.sql.Date(1212121212121L));
        transaction.setSum(new BigDecimal(200));
        return transaction;
    }
}