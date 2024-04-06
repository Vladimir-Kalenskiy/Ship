package ru.kalen.service;

import ru.kalen.model.GameData;

import java.sql.SQLException;

public interface GameService {
    int startNewGame() throws SQLException; // Запускает новую игру и возвращает идентификатор игры
    String makeMove(int gameId, double fuelRate) throws SQLException; // Выполняет ход, возвращает статус после хода
    GameData getGameStatus(int gameId) throws SQLException; // Возвращает текущее состояние игры
}
