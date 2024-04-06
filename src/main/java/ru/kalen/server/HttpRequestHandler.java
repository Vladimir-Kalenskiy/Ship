package ru.kalen.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import ru.kalen.service.GameService;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final GameService gameService; // Сервис, который управляет логикой игры

    public HttpRequestHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }

        // Парсинг URL и выделение параметров
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        String path = queryStringDecoder.path();

        FullHttpResponse response;
        switch (path) {
            case "/startGame":
                int gameId = gameService.startNewGame();
                response = createResponse(HttpResponseStatus.OK, "Game started with ID: " + gameId);
                break;
            case "/makeMove":
                // Здесь должна быть логика для извлечения параметров из запроса и выполнения хода
                int id = Integer.parseInt(queryStringDecoder.parameters().get("gameId").get(0));
                double fuelRate = Double.parseDouble(queryStringDecoder.parameters().get("fuelRate").get(0));
                String result = gameService.makeMove(id, fuelRate);
                response = createResponse(HttpResponseStatus.OK, "Move result: " + result);
                break;
            case "/loadGame":
                String idGame = queryStringDecoder.parameters().get("gameId").get(0);  //getQueryParam(decoder, "gameId");
                // логика загрузки игры
                response = createResponse(HttpResponseStatus.OK,"Game loaded " + idGame);
                break;
            default:
                response = createResponse(HttpResponseStatus.NOT_FOUND, "404 Not Found");
                break;
        }

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static FullHttpResponse createResponse(HttpResponseStatus status, String content) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}