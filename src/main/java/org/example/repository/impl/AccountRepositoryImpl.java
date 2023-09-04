package org.example.repository.impl;

import org.example.connection.DbConnection;
import org.example.exeption.ConnectionException;
import org.example.exeption.EntityNotFoundException;
import org.example.model.dto.Receipt;
import org.example.model.entity.Account;
import org.example.model.entity.Transaction;
import org.example.repository.AccountRepository;
import org.example.service.BankService;
import org.example.service.impl.BankServiceImpl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

import static org.example.model.constant.Constants.*;

public class AccountRepositoryImpl implements AccountRepository {

    private final Optional<Connection> connection;
    private final BankService bankService = new BankServiceImpl();

    public AccountRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public Account findById(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, bank_id, user_id, balance FROM account WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Account result = new Account();
                    result.setId(rs.getLong("id"));
                    result.setBankId(rs.getLong("bank_id"));
                    result.setUserId(rs.getLong("user_id"));
                    result.setBalance(rs.getBigDecimal("balance"));
                    return result;
                }
            } catch (SQLException ex) {
                throw new EntityNotFoundException(ex);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Account create(Account account) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO account (bank_id, user_id, balance) VALUES (?,?,?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, account.getBankId());
                ps.setLong(2, account.getUserId());
                ps.setBigDecimal(3, account.getBalance());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return findById(rs.getLong(1));
                }
            } catch (SQLException ex) {
                throw new EntityNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Account update(Account account) {
        if (connection.isPresent()) {
            String sql = "UPDATE account SET bank_id = ?, user_id = ?, balance = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, account.getBankId());
                ps.setLong(2, account.getUserId());
                ps.setBigDecimal(3, account.getBalance());
                ps.setLong(4, account.getId());
                ps.executeUpdate();
                return findById(account.getId());
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM account WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
    }

    @Override
    public Receipt moneyAdd(Transaction transaction) {
        Long idFrom = transaction.getSenderAccountId();
        Account accountTo = findById(idFrom);
        accountTo.setBalance(accountTo.getBalance().add(transaction.getSum()));
        update(accountTo);
        Receipt receipt = new Receipt();
        receipt.setNumber(transaction.getId());
        receipt.setTypeTransaction("Внесение");
        receipt.setSenderAccountId(transaction.getSenderAccountId());
        receipt.setRecipientBankName(bankService.findById(accountTo.getBankId()).getName());
        receipt.setSum(transaction.getSum());
        return receipt;
    }

    @Override
    public Receipt moneyRemove(Transaction transaction) {
        Long idFrom = transaction.getSenderAccountId();
        Account accountFrom = findById(idFrom);
        BigDecimal difference = accountFrom.getBalance().subtract(transaction.getSum());
        if (difference.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(INSUFFICIENT_FUNDS_MESSAGE);
        }
        accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getSum()));
        update(accountFrom);

        Receipt receipt = new Receipt();
        receipt.setNumber(transaction.getId());
        receipt.setTypeTransaction("Изъятие");
        receipt.setSenderAccountId(transaction.getSenderAccountId());
        receipt.setSum(transaction.getSum());
        receipt.setRecipientBankName(bankService.findById(accountFrom.getId()).getName());
        return receipt;
    }

    @Override
    public Receipt moneyTransfer(Transaction transaction) {
        Long idFrom = transaction.getSenderAccountId();
        Account accountFrom = findById(idFrom);
        BigDecimal difference = accountFrom.getBalance().subtract(transaction.getSum());
        if (difference.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(INSUFFICIENT_FUNDS_MESSAGE);
        }
        Account accountTo = findById(transaction.getRecipientAccountId());
        accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getSum()));
        accountTo.setBalance(accountTo.getBalance().add(transaction.getSum()));
        update(accountFrom);
        update(accountTo);

        Receipt receipt = new Receipt();
        receipt.setNumber(transaction.getId());
        receipt.setTypeTransaction("Перевод");
        receipt.setSenderAccountId(transaction.getSenderAccountId());
        receipt.setRecipientAccountId(transaction.getRecipientAccountId());
        receipt.setSum(transaction.getSum());
        receipt.setRecipientBankName(bankService.findById(accountFrom.getBankId()).getName());
        receipt.setSenderBankName(bankService.findById(accountTo.getBankId()).getName());

        return receipt;
    }
}
