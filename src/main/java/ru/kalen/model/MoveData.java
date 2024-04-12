package ru.kalen.model;

import lombok.Data;

@Data
public class MoveData {
    private int moveId;
    private int gameId;
    private String moveDetails; // Это может быть JSON или другой формат данных, зависящий от вашей игры

    public MoveData(int moveId, int gameId, String moveDetails) {
        this.moveId = moveId;
        this.gameId = gameId;
        this.moveDetails = moveDetails;
    }
}
