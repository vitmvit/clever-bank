package org.example.repository;

import org.example.model.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionRepository {

    Transaction findById(Long id);

    List<Transaction> getAllTransactionsByUserId(Long id);

    List<Transaction> getAllTransactionsByDate(Date date);

    Transaction create(Transaction transaction);

    Transaction update(Transaction transaction);

    void delete(Long id);
}
