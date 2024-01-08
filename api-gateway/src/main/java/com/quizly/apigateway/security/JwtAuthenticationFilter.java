package com.quizly.apigateway.security;

import com.quizly.apigateway.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RefreshScope
@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final WebClient webClient;

    @Autowired
    public JwtAuthenticationFilter(RouterValidator routerValidator,WebClient.Builder webClientBuilder){
        this.routerValidator = routerValidator;
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var req = exchange.getRequest();
        if(routerValidator.isSecured.test(req)){
            if(isAuthMissing(req)){
                // Authorization header not found continue along the responsibility chain.
                return errorHandler(exchange,HttpStatus.UNAUTHORIZED);
            }
            String header = getAuthHeader(req);
            if (!header.startsWith(SecurityConstants.BEARER_PREFIX)) {
                // Malformed Header.
                return errorHandler(exchange,HttpStatus.BAD_REQUEST);
            }
            var usernameResponse = validateJwtToken(header);
            try {
                return usernameResponse.flatMap(s -> {
                            if (s.isEmpty()) {
                                // non valid token detected.
                                return errorHandler(exchange, HttpStatus.FORBIDDEN);
                            }
                            ServerWebExchange newExchange = exchange.mutate().
                                    request(exchange.getRequest().
                                            mutate().
                                            header(SecurityConstants.AUTHORIZED_USER_HEADER, s)
                                            .build())
                                    .build();

                            return chain.filter(newExchange);
                        })
                        .onErrorResume(throwable -> {
                            throw new RuntimeException(throwable);
                        });
            }catch (Exception e){
                return errorHandler(exchange, HttpStatus.NOT_FOUND);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> errorHandler(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

     // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private Mono<String> validateJwtToken(String header) {
        String token = header.replace(SecurityConstants.BEARER_PREFIX,"");
        return webClient.get()
                .uri("http://jwt-service/verify?token="+token)
                .retrieve()
                .bodyToMono(String.class);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(SecurityConstants.AUTHORIZATION_HEADER).get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(SecurityConstants.AUTHORIZATION_HEADER);
    }

}
