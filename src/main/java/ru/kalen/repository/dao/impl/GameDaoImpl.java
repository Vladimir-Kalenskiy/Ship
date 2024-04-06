package ru.kalen.repository.dao.impl;

import ru.kalen.repository.DatabaseManager;
import ru.kalen.repository.dao.GameDao;

import java.sql.*;

public class GameDaoImpl implements GameDao {
    @Override
    public int createGame() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Games DEFAULT VALUES", Statement.RETURN_GENERATED_KEYS)) {
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        }
    }
}