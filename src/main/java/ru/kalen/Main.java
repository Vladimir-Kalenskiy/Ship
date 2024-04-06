package ru.kalen;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.kalen.server.AppModule;
import ru.kalen.server.GameServer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

//        GameService service = new GameServiceImpl();
        // Запуск сервера Netty или другой компонент
        GameServer server = injector.getInstance(GameServer.class);
        try {
            // Запуск HTTP сервера
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}