package ru.kalen.repository.dao;

import ru.kalen.model.GameData;

import java.sql.SQLException;
import java.util.List;

public interface MoveDao {
    void saveMove(int gameId, double altitude, double velocity, double fuel, double fuelRate) throws SQLException; // Сохраняет ход в базе данных
    GameData getLastMove(int gameId) throws SQLException; // Возвращает последний ход игры по ID игры
    List<GameData> getAllMoves(int gameId) throws SQLException; // Возвращает все ходы игры по ID игры
}
