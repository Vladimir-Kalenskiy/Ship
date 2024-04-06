package ru.kalen.repository.dao;

import java.sql.SQLException;

public interface GameDao {
    int createGame() throws SQLException; // Возвращает ID новой игры
}
