package org.example.repository.impl;

import org.example.exeption.ConnectionException;
import org.example.model.constant.TransactionType;
import org.example.model.entity.Transaction;
import org.example.repository.TransactionRepository;
import org.example.repository.TransactionRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRepositoryTestImpl implements TransactionRepositoryTest {

    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();

    @Test
    public void findByIdPositiveTest() {
        Transaction transaction = transactionRepository.create(getTransaction());
        Assertions.assertNotNull(transactionRepository.findById(transaction.getId()));

        transactionRepository.delete(transaction.getId());
    }

    @Test
    public void findByIdNegativeTest() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionRepository.findById(Long.MAX_VALUE);
        });
        assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void getAllTransactionsByUserIdPositiveTest() {
        var list = List.of(
                transactionRepository.create(getTransaction()),
                transactionRepository.create(getTransaction()),
                transactionRepository.create(getTransaction())
        );
        var accountId = list.get(0).getSenderAccountId();
        var listResult = transactionRepository.getAllTransactionsByUserId(accountId);
        Assertions.assertEquals(list.size(), listResult.size());

        for (Transaction transaction : listResult) {
            transactionRepository.delete(transaction.getId());
        }
    }

    @Test
    public void getAllTransactionsByUserIdNegativeTest() {
        Long id = Long.MAX_VALUE;
        List<Transaction> list = transactionRepository.getAllTransactionsByUserId(id);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void getAllTransactionsByDatePositiveTest() {
        var list = List.of(
                transactionRepository.create(getTransaction()),
                transactionRepository.create(getTransaction()),
                transactionRepository.create(getTransaction())
        );

        List<Transaction> listResult = transactionRepository.getAllTransactionsByDate((Date) list.get(0).getDateTransaction());
        Assertions.assertEquals(list.size(), listResult.size());

        for (Transaction transaction : listResult) {
            transactionRepository.delete(transaction.getId());
        }
    }

    @Test
    public void getAllTransactionsByDateNegativeTest() {
        Date date = new Date(Long.MAX_VALUE);
        List<Transaction> list = transactionRepository.getAllTransactionsByDate(date);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void createTest() {
        Transaction transactionCreateDto = getTransaction();
        Transaction transactionResponseDto = transactionRepository.create(transactionCreateDto);
        Assertions.assertNotNull(transactionResponseDto.getId());
        Assertions.assertEquals(transactionCreateDto.getBankId(), transactionResponseDto.getBankId());

        transactionRepository.delete(transactionResponseDto.getId());
    }

    @Test
    public void updateTest() {
        Transaction saved = transactionRepository.create(getTransaction());
        Transaction transaction = new Transaction();
        transaction.setId(saved.getId());
        transaction.setType(saved.getType());
        transaction.setBankId(saved.getBankId());
        transaction.setSenderAccountId(saved.getSenderAccountId());
        transaction.setRecipientAccountId(saved.getRecipientAccountId());
        transaction.setDateTransaction(saved.getDateTransaction());
        transaction.setSum(new BigDecimal(500));
        Transaction updated = transactionRepository.update(transaction);
        Assertions.assertNotEquals(saved.getSum(), updated.getSum());

        transactionRepository.delete(saved.getId());
    }

    @Test
    public void deleteTest() {
        Transaction saved = transactionRepository.create(getTransaction());
        transactionRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.T);
        transaction.setBankId(1L);
        transaction.setSenderAccountId(2L);
        transaction.setRecipientAccountId(3L);
        transaction.setDateTransaction(new Date(2017 - 01 - 13));
        transaction.setSum(new BigDecimal(200));
        return transaction;
    }
}
