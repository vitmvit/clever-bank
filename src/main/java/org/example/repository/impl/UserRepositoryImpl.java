package org.example.repository.impl;

import org.example.connection.DbConnection;
import org.example.exeption.ConnectionException;
import org.example.model.entity.User;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.Optional;

import static org.example.model.constant.Constants.QUERY_ERROR;


public class UserRepositoryImpl implements UserRepository {

    private final Optional<Connection> connection;

    public UserRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public User findById(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, name FROM client WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User result = new User();
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
    public User create(User user) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO client (name) VALUES (?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getName());
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
    public User update(User user) {
        if (connection.isPresent()) {
            String sql = "UPDATE client SET name = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setString(1, user.getName());
                ps.setLong(2, user.getId());
                ps.executeUpdate();
                return findById(user.getId());
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM client WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(QUERY_ERROR);
            }
        }
    }
}
