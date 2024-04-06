package ru.kalen.server;

import com.google.inject.Injector;
import io.netty.channel.ChannelHandler;

public class HttpHandlerFactory {
    private final Injector injector;

    public HttpHandlerFactory(Injector injector) {
        this.injector = injector;
    }

    public ChannelHandler createHandler() {
        return injector.getInstance(HttpRequestHandler.class);
    }
}
