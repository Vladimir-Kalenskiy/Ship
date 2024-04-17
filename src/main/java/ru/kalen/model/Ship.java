package ru.kalen.model;

import lombok.Data;

@Data
public class Ship {
    private double altitude;    // Высота над поверхностью
    private double velocity;    // Вертикальная скорость
    private double fuel;        // Оставшееся топливо
    private double fuelRate;    // Расход топлива за ход

    // Конструктор для инициализации корабля
    public Ship(double altitude, double velocity, double fuel) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuel = fuel;
        this.fuelRate = 0;
    }

    // Метод для обновления состояния корабля
    public void update(double timeStep, double gravity, double thrustAcceleration) {
        if (fuelRate > fuel) {
            fuelRate = fuel; // Нельзя потратить больше топлива, чем есть
        }

        double acceleration = gravity - thrustAcceleration ; //thrustAcceleration - gravity;  // Общее ускорение
        velocity += acceleration * timeStep;                // Обновление скорости
        altitude -= velocity * timeStep;                    // Обновление высоты
        fuel -= fuelRate;                                   // Уменьшение топлива

        // Нельзя уйти в отрицательное топливо
        if (fuel < 0) {
            fuel = 0;
        }
    }
}
