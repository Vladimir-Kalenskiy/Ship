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

    @Override
    public void updateGameStatus(int gameId, String status) throws SQLException {
        String sql = "UPDATE games SET status = ? WHERE gameId = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating game status", e);
        }
    }
}