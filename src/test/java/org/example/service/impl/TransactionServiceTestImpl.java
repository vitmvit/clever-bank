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

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;

class TransactionServiceTestImpl implements TransactionServiceTest {

    private final TransactionService transactionService = new TransactionServiceImpl();

    @Test
    public void findByIdPositiveTest() {
        TransactionResponseDto transactionResponseDto = transactionService.create(getTransaction());
        Assertions.assertNotNull(transactionService.findById(transactionResponseDto.getId()));
    }

    @Test
    public void findByIdNegativeTest() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void getAllTransactionsByUserIdPositiveTest() {
        List<TransactionResponseDto> list = new ArrayList<>();
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));

        List<TransactionResponseDto> listResult = transactionService.getAllTransactionsByUserId(list.get(0).getSenderAccountId());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByUserIdNegativeTest() {
        Long id = Long.MAX_VALUE;
        List<TransactionResponseDto> list = transactionService.getAllTransactionsByUserId(id);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void getAllTransactionsByDatePositiveTest() {
        List<TransactionResponseDto> list = new ArrayList<>();
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));
        list.add(transactionService.create(getTransaction()));

        List<TransactionResponseDto> listResult = transactionService.getAllTransactionsByDate(list.get(0).getDateTransaction());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByDateNegativeTest() {
        Date date = new Date(1212121212121L);
        List<TransactionResponseDto> list = transactionService.getAllTransactionsByDate(date);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void createPositiveTest() {
        MoneyOperationDto moneyOperationDto = getTransaction();
        TransactionResponseDto transactionResponseDto = transactionService.create(moneyOperationDto);
        Assertions.assertNotNull(transactionResponseDto.getId());
        Assertions.assertEquals(moneyOperationDto.bankId(), transactionResponseDto.getBankId());
    }

    @Test
    public void createNegativeTest() {
        MoneyOperationDto moneyOperationDto = new MoneyOperationDto(null, null, null, null, null);
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            transactionService.create(moneyOperationDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void updatePositiveTest() {
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
    public void updateNegativeTest() {
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
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void deleteTest() {
        TransactionResponseDto saved = transactionService.create(getTransaction());
        transactionService.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    private MoneyOperationDto getTransaction() {
        return new MoneyOperationDto(1L, 2L, 3L, (java.sql.Date) new Date(), new BigDecimal(200));
    }
}