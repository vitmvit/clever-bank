package org.example.repository;

import org.example.model.dto.Receipt;
import org.example.model.entity.Account;
import org.example.model.entity.Transaction;

public interface AccountRepository {

    Account findById(Long id);

    Account create(Account account);

    Account update(Account account);

    void delete(Long id);

    Receipt moneyAdd(Transaction transaction);

    Receipt moneyRemove(Transaction transaction);

    Receipt moneyTransfer(Transaction transaction);
}
