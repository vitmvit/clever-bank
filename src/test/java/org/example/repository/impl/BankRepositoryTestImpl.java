package org.example.repository.impl;

import org.example.exeption.ConnectionException;
import org.example.model.entity.Bank;
import org.example.repository.BankRepository;
import org.example.repository.BankRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;

public class BankRepositoryTestImpl implements BankRepositoryTest {

    private final BankRepository bankRepository = new BankRepositoryImpl();

    @Test
    public void findByIdPositiveTest() {
        Bank bank = bankRepository.create(getBank());
        Assertions.assertNotNull(bankRepository.findById(bank.getId()));

        bankRepository.delete(bank.getId());
    }

    @Test
    public void findByIdNegativeTest() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            bankRepository.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createTest() {
        Bank bankCreate = getBank();
        Bank saved = bankRepository.create(getBank());
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(bankCreate.getName(), saved.getName());

        bankRepository.delete(saved.getId());
    }

    @Test
    public void updateTest() {
        Bank saved = bankRepository.create(getBank());
        Bank bank = new Bank();
        bank.setId(saved.getId());
        bank.setName("Name");
        Bank updated = bankRepository.update(bank);
        Assertions.assertNotEquals(saved.getName(), updated.getName());

        bankRepository.delete(saved.getId());
    }

    @Test
    public void deleteTest() {
        Bank saved = bankRepository.create(getBank());
        bankRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            bankRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));

        bankRepository.delete(saved.getId());
    }

    private Bank getBank() {
        Bank bank = new Bank();
        bank.setName("bank_1");
        return bank;
    }
}
