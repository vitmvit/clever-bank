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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRepositoryTestImpl implements TransactionRepositoryTest {

    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();

    @Test
    public void findByIdPositive() {
        Transaction transaction = transactionRepository.create(getTransaction());
        Assertions.assertNotNull(transactionRepository.findById(transaction.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionRepository.findById(Long.MAX_VALUE);
        });
        assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void getAllTransactionsByUserIdPositive() {
        List<Transaction> list = new ArrayList<>();
        list.add(transactionRepository.create(getTransaction()));
        list.add(transactionRepository.create(getTransaction()));
        list.add(transactionRepository.create(getTransaction()));

        List<Transaction> listResult = transactionRepository.getAllTransactionsByUserId(list.get(0).getSenderAccountId());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByUserIdNegative() {
        Long id = Long.MAX_VALUE;
        List<Transaction> list = transactionRepository.getAllTransactionsByUserId(id);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void getAllTransactionsByDatePositive() {
        List<Transaction> list = new ArrayList<>();
        list.add(transactionRepository.create(getTransaction()));
        list.add(transactionRepository.create(getTransaction()));
        list.add(transactionRepository.create(getTransaction()));

        List<Transaction> listResult = transactionRepository.getAllTransactionsByDate(list.get(0).getDateTransaction());

        Assertions.assertEquals(list.size(), listResult.size());
    }

    @Test
    public void getAllTransactionsByDateNegative() {
        Date date = new Date(1212121212121L);
        List<Transaction> list = transactionRepository.getAllTransactionsByDate(date);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void create() {
        Transaction transactionCreateDto = getTransaction();
        Transaction transactionResponseDto = transactionRepository.create(transactionCreateDto);
        Assertions.assertNotNull(transactionResponseDto.getId());
        Assertions.assertEquals(transactionCreateDto.getBankId(), transactionResponseDto.getBankId());
    }

    @Test
    public void update() {
        Transaction saved = transactionRepository.create(getTransaction());

        Transaction transaction = new Transaction();
        transaction.setId(saved.getId());
        transaction.setType(saved.getType());
        transaction.setBankId(saved.getBankId());
        transaction.setSenderAccountId(saved.getSenderAccountId());
        transaction.setRecipientAccountId(saved.getRecipientAccountId());
        transaction.setDateTransaction(saved.getDateTransaction());
        transaction.setSum(new BigDecimal(500));
// TODO: 04.09.2023 пересмотреть transactionType
        Transaction updated = transactionRepository.update(transaction);
        Assertions.assertNotEquals(saved.getSum(), updated.getSum());
    }

    @Test
    public void delete() {
        Transaction saved = transactionRepository.create(getTransaction());
        transactionRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            transactionRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setBankId(1l);
        transaction.setSenderAccountId(2l);
        transaction.setRecipientAccountId(3l);
        transaction.setDateTransaction(new Date(1212121212121L));
        transaction.setSum(new BigDecimal(200));
        return transaction;
    }
}
