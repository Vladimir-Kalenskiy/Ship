package ru.kalen.repository.dao.impl;

import ru.kalen.model.GameData;
import ru.kalen.repository.DatabaseManager;
import ru.kalen.repository.dao.MoveDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDaoImpl implements MoveDao {
    @Override
    public void saveMove(int gameId, double altitude, double velocity, double fuel, double fuelRate) throws SQLException {
        String sql = "INSERT INTO Moves (gameId, altitude, velocity, fuel, fuelRate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            stmt.setDouble(2, altitude);
            stmt.setDouble(3, velocity);
            stmt.setDouble(4, fuel);
            stmt.setDouble(5, fuelRate);
            stmt.executeUpdate();
        }
    }

    @Override
    public GameData getLastMove(int gameId) throws SQLException {
        String sql = "SELECT * FROM Moves WHERE gameId = ? ORDER BY moveId DESC LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new GameData(
                        gameId,
                        rs.getDouble("altitude"),
                        rs.getDouble("velocity"),
                        rs.getDouble("fuel"),
                        rs.getDouble("fuelRate")
                );
            }
        }
        return null;
    }

    @Override
    public List<GameData> getAllMoves(int gameId) throws SQLException {
        List<GameData> moves = new ArrayList<>();
        String sql = "SELECT * FROM Moves WHERE gameId = ? ORDER BY moveId";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                moves.add(new GameData(
                        gameId,
                        rs.getDouble("altitude"),
                        rs.getDouble("velocity"),
                        rs.getDouble("fuel"),
                        rs.getDouble("fuelRate")
                ));
            }
        }
        return moves;
    }
}
