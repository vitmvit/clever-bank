package org.example.repository.impl;

import org.example.connection.DbConnection;
import org.example.exeption.ConnectionException;
import org.example.exeption.EntityNotFoundException;
import org.example.model.constant.TransactionType;
import org.example.model.entity.Transaction;
import org.example.repository.TransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.model.constant.Constants.QUERY_ERROR;
import static org.example.model.constant.Constants.TRANSACTION_NOT_FOUND_MESSAGE;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final Optional<Connection> connection;

    public TransactionRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public Transaction findById(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, t_type, bank_id, sender_account_id, recipient_account_id, date_transaction, sum FROM transaction WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Transaction result = new Transaction();
                    result.setId(rs.getLong("id"));
                    result.setType(TransactionType.valueOf(rs.getString("t_type")));
                    result.setBankId(rs.getLong("bank_id"));
                    result.setSenderAccountId(rs.getLong("sender_account_id"));
                    result.setRecipientAccountId(rs.getLong("recipient_account_Id"));
                    result.setDateTransaction(rs.getDate("date_transaction"));
                    result.setSum(rs.getBigDecimal("sum"));
                    return result;
                }
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public List<Transaction> getAllTransactionsByUserId(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, t_type, bank_id, sender_account_id, recipient_account_id, date_transaction, sum FROM transaction WHERE sender_account_id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                List<Transaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    Transaction result = new Transaction();
                    result.setId(rs.getLong("id"));
                    result.setType(TransactionType.valueOf(rs.getString("t_type")));
                    result.setBankId(rs.getLong("bank_id"));
                    result.setSenderAccountId(rs.getLong("sender_account_id"));
                    result.setRecipientAccountId(rs.getLong("recipient_account_id"));
                    result.setDateTransaction(rs.getDate("date_transaction"));
                    result.setSum(rs.getBigDecimal("sum"));
                    transactions.add(result);
                }
                return transactions;
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public List<Transaction> getAllTransactionsByDate(Date date) {
        if (connection.isPresent()) {
            String sql = "SELECT id, t_type, bank_id, sender_account_id, recipient_account_id, date_transaction, sum FROM transaction WHERE date_transaction = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setDate(1, new java.sql.Date(date.getTime()));
                ResultSet rs = ps.executeQuery();
                List<Transaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    Transaction result = new Transaction();
                    result.setId(rs.getLong("id"));
                    result.setType(TransactionType.valueOf(rs.getString("t_type")));
                    result.setBankId(rs.getLong("bank_id"));
                    result.setSenderAccountId(rs.getLong("sender_account_id"));
                    result.setRecipientAccountId(rs.getLong("recipient_account_id"));
                    result.setDateTransaction(rs.getDate("date_transaction"));
                    result.setSum(rs.getBigDecimal("sum"));
                    transactions.add(result);
                }
                return transactions;
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Transaction create(Transaction transaction) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO transaction (t_type, bank_id, sender_account_id, recipient_account_id, date_transaction, sum) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, transaction.getType().name());
                ps.setLong(2, transaction.getBankId());
                ps.setLong(3, transaction.getSenderAccountId());
                ps.setLong(4, transaction.getRecipientAccountId() == null ? 0 : transaction.getRecipientAccountId());
                ps.setDate(5, new Date(transaction.getDateTransaction().getTime()));
                ps.setBigDecimal(6, transaction.getSum());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return findById(rs.getLong(1));
                }
            } catch (SQLException ex) {
                throw new EntityNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (connection.isPresent()) {
            String sql = "UPDATE transaction SET t_type = ?, bank_id = ?, sender_account_id = ?, recipient_account_id = ?, date_transaction = ?, sum = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setString(1, transaction.getType().getName());
                ps.setLong(2, transaction.getBankId());
                ps.setLong(3, transaction.getSenderAccountId());
                ps.setLong(4, transaction.getRecipientAccountId());
                ps.setDate(5, new Date(transaction.getDateTransaction().getTime()));
                ps.setBigDecimal(6, transaction.getSum());
                ps.setLong(7, transaction.getId());
                ps.executeUpdate();
                return findById(transaction.getId());
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM transaction WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
    }
}
