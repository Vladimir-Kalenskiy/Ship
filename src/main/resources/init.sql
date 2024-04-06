CREATE TABLE IF NOT EXISTS Games (
                                     gameId INT AUTO_INCREMENT PRIMARY KEY,
                                     createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS Moves (
                                     moveId INT AUTO_INCREMENT PRIMARY KEY,
                                     gameId INT,
                                     stepNumber INT,
                                     altitude DOUBLE,
                                     velocity DOUBLE,
                                     fuel DOUBLE,
                                     fuelRate DOUBLE,
                                     FOREIGN KEY (gameId) REFERENCES Games(gameId)
);