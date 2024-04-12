package ru.kalen.service.impl;

import com.google.inject.Inject;
import ru.kalen.model.GameData;
import ru.kalen.model.GameLogic;
import ru.kalen.model.Ship;
import ru.kalen.repository.dao.GameDao;
import ru.kalen.repository.dao.MoveDao;
import ru.kalen.service.GameService;

import java.sql.SQLException;

public class GameServiceImpl implements GameService {
    private final GameDao gameDao;
    private final MoveDao moveDao;

    @Inject
    public GameServiceImpl(GameDao gameDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.moveDao = moveDao;
    }

    @Override
    public int startNewGame() throws SQLException {
//        moveDao.
        // Создает новую игру в базе данных и возвращает ее ID
        return gameDao.createGame();
    }

    @Override
    public String makeMove(int gameId, double fuelRate) throws SQLException {
        // Получить текущее состояние игры из базы данных
        GameData gameData = moveDao.getLastMove(gameId);
        if (gameData == null) {
            // Создать объект Ship с текущими данными
            Ship ship = new Ship(100, 0, 140);
            GameLogic gameLogic = new GameLogic(ship);

            // Выполнить ход и получить результат
            String result = gameLogic.performStep(fuelRate);

            // Сохранить новое состояние игры в базе данных
            moveDao.saveMove(gameId, ship.getAltitude(), ship.getVelocity(), ship.getFuel(), fuelRate);

            return result;
//            throw new IllegalStateException("Game not found!");
        }

        // Создать объект Ship с текущими данными
        Ship ship = new Ship(gameData.getAltitude(), gameData.getVelocity(), gameData.getFuel());
        GameLogic gameLogic = new GameLogic(ship);

        // Выполнить ход и получить результат
        String result = gameLogic.performStep(fuelRate);

        // Сохранить новое состояние игры в базе данных
        moveDao.saveMove(gameId, ship.getAltitude(), ship.getVelocity(), ship.getFuel(), fuelRate);

        return result;
    }

    @Override
    public GameData getGameStatus(int gameId) throws SQLException {
        // Получить последнее состояние игры из базы данных
        return moveDao.getLastMove(gameId);
    }
}
