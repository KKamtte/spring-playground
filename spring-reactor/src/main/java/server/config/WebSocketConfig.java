package server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import server.handler.EchoWebSocketHandler;
import server.handler.TimeWebSocketHandler;

import java.util.Map;

/*
    --> GET /ws/echo HTTP/1.1

    Host: localhost:8080
    Upgrade: websocket
    Connection: Upgrade
    Sec-WebSocket-Key: ...
    Sec-WebSocket-Version: 13

    --> 101 Switching Protocols HTTP/1.1

    Host: localhost:8080
    Upgrade: websocket
    Connection: Upgrade
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping webSocketHandlerMapping(
            EchoWebSocketHandler echoWebSocketHandler,
            TimeWebSocketHandler timeWebSocketHandler) {
        Map<String, WebSocketHandler> map = Map.of(
                "/ws/echo", echoWebSocketHandler,
                "/ws/time", timeWebSocketHandler);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(-1);

        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
