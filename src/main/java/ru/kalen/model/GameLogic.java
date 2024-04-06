package ru.kalen.model;

public class GameLogic {
    private Ship ship;
    private double gravity = 1.62; // Гравитация на Луне, м/с^2
    private double thrustPerFuelUnit = 0.15; // Ускорение на единицу топлива
    private final double maxLandingVelocity = 5.0; // Максимально допустимая скорость при посадке

    public GameLogic(Ship ship) {
        this.ship = ship;
    }

    public String performStep(double fuelRate) {
        double thrustAcceleration = fuelRate * thrustPerFuelUnit;
        ship.setFuelRate(fuelRate);
        ship.update(1.0, gravity, thrustAcceleration); // Обновление на каждый ход

        // Проверка условий посадки или крушения
        if (ship.getAltitude() <= 0) {
            if (Math.abs(ship.getVelocity()) <= maxLandingVelocity) {
                return "Successful landing!";
            } else {
                return "Crash! Velocity was too high.";
            }
        }
        return "Flying...";
    }
}
