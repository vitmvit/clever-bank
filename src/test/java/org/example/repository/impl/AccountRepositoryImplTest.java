package org.example.repository.impl;

import org.example.exeption.ConnectionException;
import org.example.model.constant.TransactionType;
import org.example.model.entity.Account;
import org.example.model.entity.Transaction;
import org.example.repository.AccountRepository;
import org.example.repository.AccountRepositoryTest;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.INSUFFICIENT_FUNDS_MESSAGE;

public class AccountRepositoryImplTest implements AccountRepositoryTest {

    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();

    @Override
    @Test
    public void findByIdPositiveTest() {
        Account account = accountRepository.create(getAccount());
        Assertions.assertNotNull(accountRepository.findById(account.getId()));

        accountRepository.delete(account.getId());
    }

    @Test
    public void findByIdNegativeTest() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            accountRepository.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createTest() {
        Account account = getAccount();
        Account saved = accountRepository.create(account);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(account.getBankId(), saved.getBankId());

        accountRepository.delete(saved.getId());
    }

    @Test
    public void updateTest() {
        Account saved = accountRepository.create(getAccount());
        Account account = new Account();
        account.setId(saved.getId());
        account.setBankId(saved.getBankId());
        account.setUserId(saved.getUserId());
        account.setBalance(new BigDecimal(Long.MAX_VALUE));
        Account updated = accountRepository.update(account);
        Assertions.assertNotEquals(saved.getBalance(), updated.getBalance());
    }

    @Test
    public void deleteTest() {
        Account saved = accountRepository.create(getAccount());
        accountRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            accountRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));

        accountRepository.delete(saved.getId());
    }

    @Test
    public void moneyTransferPositiveTest() {
        Account accountFrom = getAccount();
        Account accountFromSaved = accountRepository.create(accountFrom);
        Account accountTo = getAccount();
        Account accountToSaved = accountRepository.create(accountTo);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.T);
        transaction.setBankId(1L);
        transaction.setSenderAccountId(accountFromSaved.getId());
        transaction.setRecipientAccountId(accountToSaved.getId());
        transaction.setDateTransaction(new Date(2017 - 01 - 13));
        transaction.setSum(BigDecimal.valueOf(100));
        Transaction transactionSaved = transactionRepository.create(transaction);
        accountRepository.moneyTransfer(transactionSaved);
        Assertions.assertEquals(accountFrom.getBalance().subtract(new BigDecimal(100)), accountRepository.findById(accountFromSaved.getId()).getBalance());
        Assertions.assertEquals(accountTo.getBalance().add(new BigDecimal(100)), accountRepository.findById(accountToSaved.getId()).getBalance());

        accountRepository.delete(accountFromSaved.getId());
        accountRepository.delete(accountToSaved.getId());
        accountRepository.delete(transactionSaved.getId());
    }

    @Test
    public void moneyTransferNegativeTest() {
        Account accountFrom = getAccount();
        Account accountFromSaved = accountRepository.create(accountFrom);
        Account accountTo = getAccount();
        Account accountToSaved = accountRepository.create(accountTo);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.T);
        transaction.setBankId(1L);
        transaction.setSenderAccountId(accountFromSaved.getId());
        transaction.setRecipientAccountId(accountToSaved.getId());
        transaction.setDateTransaction(new Date(2017 - 01 - 13));
        transaction.setSum(accountFromSaved.getBalance().add(new BigDecimal(100)));
        Transaction transactionSaved = transactionRepository.create(transaction);
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            accountRepository.moneyTransfer(transactionSaved);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(INSUFFICIENT_FUNDS_MESSAGE));

        accountRepository.delete(accountFromSaved.getId());
        accountRepository.delete(accountToSaved.getId());
        accountRepository.delete(transactionSaved.getId());
    }

    private Account getAccount() {
        Account account = new Account();
        account.setBankId(1L);
        account.setUserId(2L);
        account.setBalance(new BigDecimal(700));
        return account;
    }
}
