//package in.devdanish.payDan.paymentMs.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.graphql.web.WebGraphQlHandler;
//import org.springframework.graphql.web.websocket.GraphQlWebSocketHandler;
//import org.springframework.graphql.web.websocket.GraphQlWebSocketInterceptor;
//import org.springframework.graphql.web.websocket.GraphQlWebSocketMessage;
//import org.springframework.web.reactive.HandlerMapping;
//import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
//import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
//import org.springframework.web.reactive.socket.server.support.WebSocketHandlerMapping;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Collections;
//
//@Configuration
//public class GraphQLWebSocketConfig {
//
//    @Bean
//    public HandlerMapping graphQlWebSocketMapping(WebGraphQlHandler handler) {
//        GraphQlWebSocketHandler webSocketHandler =
//                new GraphQlWebSocketHandler(handler, new GraphQlWebSocketInterceptor() {
//                    @Override
//                    public void handleConnectionInitialization(GraphQlWebSocketMessage message, WebSocketSession session) {
//                        // Optional: Auth or setup logic
//                    }
//                });
//
//        return new SimpleUrlHandlerMapping() {{
//            setUrlMap(Collections.singletonMap("/graphql", webSocketHandler));
//            setOrder(-1); // High priority
//        }};
//    }
//
//    @Bean
//    public WebSocketHandlerAdapter handlerAdapter() {
//        return new WebSocketHandlerAdapter();
//    }
//}
