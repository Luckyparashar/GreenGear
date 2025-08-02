package com.greengear.filter;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component //to declare spring bean

public class JwtUtils {
//inject the props in JWT Utils class for creating n validation of JWT
	/*
	 * @Value => injection of a value (<constr-arg name n value : xml tags) arg - Spring
	 * expression Lang - SpEL
	 * // example of value injected as dependency , using SpEL
	 * (Spring Expression Language)
	 */
	@Value("${jwt.secret.key}") 	
	private String jwtSecret;

	@Value("${jwt.expiration.time}")
	private int jwtExpirationMs;

	private SecretKey key;//=> represents symmetric key

	@PostConstruct
	public void init() {
		
		/*create secret key instance from  Keys class
		 * Keys - builder of Secret key
		 * Create a Secret Key using HMAC-SHA256 encryption algo.
		 */		
		key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}


	// this method will be invoked by our custom JWT filter
	public String getUserNameFromJwtToken(Claims claims) {
		
		return claims.getSubject();
	}

	// this method will be invoked by our custom JWT filter
	public Claims validateJwtToken(String jwtToken) {
	
		Claims claims = Jwts.parser()

				.verifyWith(key) // sets the SAME secret key for JWT signature verification
				.build()

				// rets the JWT parser set with the Key
				.parseSignedClaims(jwtToken) // rets JWT with Claims added in the body
				.getPayload();// => JWT valid , rets the Claims(payload)
		/*
		 * parseClaimsJws - throws:UnsupportedJwtException -if the JWT body | payload
		 * does not represent any Claims JWSMalformedJwtException - if the JWT body |
		 * payload is not a valid JWSSignatureException - if the JWT signature
		 * validation fails ExpiredJwtException - if the specified JWT is expired
		 * IllegalArgumentException - if the JWT claims body | payload is null or empty
		 * or only whitespace
		 */
		return claims;
	}

	

	

	}


