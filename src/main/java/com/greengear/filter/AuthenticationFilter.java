package com.greengear.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
public static class Config{}

@Autowired
private  RouteValidator validator;


@Autowired
private JwtUtils jwtUtil;

public AuthenticationFilter() {
    super(Config.class);
}

@Override
public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("Incoming request path: " + path);
        boolean secured = validator.isSecured.test(exchange.getRequest());
        System.out.println("Is secured route: " + secured);

        if (secured) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                System.out.println("Missing Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            try {
                jwtUtil.validateJwtToken(authHeader);
            } catch (Exception e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
System.out.println("sxkxnknxsxmkm");
        return chain.filter(exchange);
    });
}



}
