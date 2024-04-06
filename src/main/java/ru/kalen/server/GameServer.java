package ru.kalen.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import ru.kalen.service.GameService;

public class GameServer {
    private final int port;
    private final GameService gameService;

    @Inject
    public GameServer(@Named("ServerPort") int port, GameService gameService) {
        this.port = port;
        this.gameService = gameService;
    }

    public void start() throws InterruptedException {
        // Создаем группы потоков для обработки подключений
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new HttpServerCodec());
                            p.addLast(new HttpRequestHandler(gameService)); // Используем наш HttpRequestHandler
                            p.addLast(new StaticFileHandler("src/main/resources/")); // Укажите путь к вашему веб-корню
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Запускаем сервер
            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server started on port " + port);
            f.channel().closeFuture().sync();
        } finally {
            // Закрываем группы потоков
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
