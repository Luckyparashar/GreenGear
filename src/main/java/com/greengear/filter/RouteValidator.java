package com.greengear.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of(
		    "/user/signup", 
		    "/user/signin",
		    "/users/batch",
		    "/eureka",
		    "/ms3/booking",
		    "/ms3/payment/webhook",
		    "/ms2/equipment/1/avl",
		    "/ms2/equipment",           // FIXED
		    "/ms4/review/1"
		    ,"/user/users/batch"
		);


	public Predicate<ServerHttpRequest> isSecured =
		    request -> openApiEndpoints
		        .stream()
		        .noneMatch(uri -> request.getURI().getPath().startsWith(uri));

}
