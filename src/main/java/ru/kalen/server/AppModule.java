package ru.kalen.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import ru.kalen.repository.dao.GameDao;
import ru.kalen.repository.dao.impl.GameDaoImpl;
import ru.kalen.repository.dao.MoveDao;
import ru.kalen.repository.dao.impl.MoveDaoImpl;
import ru.kalen.service.GameService;
import ru.kalen.service.impl.GameServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        // Связывание интерфейсов DAO с их реализациями
        bind(GameDao.class).to(GameDaoImpl.class).in(Singleton.class);
        bind(MoveDao.class).to(MoveDaoImpl.class).in(Singleton.class);

        // Если есть контроллеры или другие сервисы, их тоже можно здесь сконфигурировать
        bind(GameService.class).to(GameServiceImpl.class).in(Singleton.class);
        bind(Integer.class).annotatedWith(Names.named("ServerPort")).toInstance(8080);
        // Пример для Netty обработчиков
    }
    @Provides
    HttpRequestHandler provideHttpRequestHandler(GameService gameService) {
        return new HttpRequestHandler(gameService);
    }
}