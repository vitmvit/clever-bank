package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;
import org.example.service.TransactionService;
import org.example.service.TransactionServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TransactionServiceTestImpl implements TransactionServiceTest {

    private final TransactionService transactionService = new TransactionServiceImpl();

    @Test
    public void findByIdPositive() {
        TransactionResponseDto transactionResponseDto = transactionService.create(getTransaction());
        Assertions.assertNotNull(transactionService.findById(transactionResponseDto.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void getAllTransactionsByUserIdPositive() {
        List<TransactionResponseDto> list = new ArrayList<>();
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));

        List<TransactionResponseDto> listResult = transactionService.getAllTransactionsByUserId(list.get(0).getSenderAccountId());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByUserIdNegative() {
        Long id = Long.MAX_VALUE;
        List<TransactionResponseDto> list = transactionService.getAllTransactionsByUserId(id);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void getAllTransactionsByDatePositive() {
        List<TransactionResponseDto> list = new ArrayList<>();
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));

        List<TransactionResponseDto> listResult = transactionService.getAllTransactionsByDate(list.get(0).getDateTransaction());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByDateNegative() {
        Date date = new Date(1212121212121L);
        List<TransactionResponseDto> list = transactionService.getAllTransactionsByDate(date);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void createPositive() {
        MoneyOperationDto moneyOperationDto = getTransaction();
        TransactionResponseDto transactionResponseDto = transactionService.create(moneyOperationDto);
        Assertions.assertNotNull(transactionResponseDto.getId());
        Assertions.assertEquals(moneyOperationDto.bankId(), transactionResponseDto.getBankId());
    }

    @Test
    public void createNegative() {
        MoneyOperationDto moneyOperationDto = new MoneyOperationDto(null, null, null, null, null);
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            transactionService.create(moneyOperationDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void updatePositive() {
        MoneyOperationDto accountCreateDto = getTransaction();

        TransactionResponseDto saved = transactionService.create(accountCreateDto);

        TransactionUpdateDto transaction = new TransactionUpdateDto();
        transaction.setId(saved.getId());
        transaction.setBankId(saved.getBankId());
        transaction.setSenderAccountId(saved.getSenderAccountId());
        transaction.setRecipientAccountId(saved.getRecipientAccountId());
        transaction.setDateTransaction((java.sql.Date) saved.getDateTransaction());
        transaction.setSum(new BigDecimal(500));

        TransactionResponseDto updated = transactionService.update(transaction);
        Assertions.assertNotEquals(saved.getSum(), updated.getSum());
    }

    @Test
    public void updateNegative() {
        TransactionUpdateDto transaction = new TransactionUpdateDto();
        transaction.setId(null);
        transaction.setBankId(null);
        transaction.setSenderAccountId(null);
        transaction.setRecipientAccountId(null);
        transaction.setDateTransaction(null);
        transaction.setSum(null);

        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            transactionService.update(transaction);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void delete() {
        TransactionResponseDto saved = transactionService.create(getTransaction());
        transactionService.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    private MoneyOperationDto getTransaction() {
        return new MoneyOperationDto(1L, 2L, 3L, (java.sql.Date) new Date(), new BigDecimal(200));
    }
}