package org.example.repository;

import org.example.model.entity.Bank;

public interface BankRepository {

    Bank findById(Long id);

    Bank create(Bank bank);

    Bank update(Bank user);

    void delete(Long id);
}
