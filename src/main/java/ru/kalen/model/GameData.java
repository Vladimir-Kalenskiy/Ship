package ru.kalen.model;

import lombok.Data;

@Data
public class GameData {
    private final int gameId;
    private final double altitude;
    private final double velocity;
    private final double fuel;
    private final double fuelRate;
    private String statusGame;

    public GameData(int gameId, double altitude, double velocity, double fuel, double fuelRate) {
        this.gameId = gameId;
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuel = fuel;
        this.fuelRate = fuelRate;
        this.statusGame = "Failed";
    }
}
