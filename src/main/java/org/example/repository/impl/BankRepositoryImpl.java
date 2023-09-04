package org.example.repository.impl;

import org.example.connection.DbConnection;
import org.example.exeption.ConnectionException;
import org.example.model.entity.Bank;
import org.example.repository.BankRepository;

import java.sql.*;
import java.util.Optional;

import static org.example.model.constant.Constants.QUERY_ERROR;

public class BankRepositoryImpl implements BankRepository {
    private final Optional<Connection> connection;

    public BankRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public Bank findById(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, name FROM bank WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Bank result = new Bank();
                    result.setId(rs.getLong("id"));
                    result.setName(rs.getString("name"));
                    return result;
                }
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Bank create(Bank bank) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO bank (name) VALUES (?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, bank.getName());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return findById(rs.getLong(1));
                }
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Bank update(Bank bank) {
        if (connection.isPresent()) {
            String sql = "UPDATE bank SET name = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setString(1, bank.getName());
                ps.setLong(2, bank.getId());
                ps.executeUpdate();
                return findById(bank.getId());
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM bank WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
    }
}
