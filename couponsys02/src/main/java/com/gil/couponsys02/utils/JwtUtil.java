package com.gil.couponsys02.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.exceptions.ErrorsMessages;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.services.ClientService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {

	private static final String SECRET_KEY = "secret";
	private static final String SERVICE_ID = "service";
	private static final String TYPE_KEY = "type";
	

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public ClientType extractClientType(String token) {
		final Claims claims = extractAllClaims(token);
		return ClientType.valueOf((String)claims.get(TYPE_KEY));
	}

		

	public int extractClientServiceId(String token) {
		final Claims claims = extractAllClaims(token);
		return (int) claims.get(SERVICE_ID);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(ClientService clientService, ClientType clientType, UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(SERVICE_ID, ServicesUtil.getServiceId(clientService));
		claims.put(TYPE_KEY, clientType);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
		.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails, ClientType clientTypeRequest) throws ExpiredJwtException {
		isTokenExpired(token);
		return extractClientType(token) == clientTypeRequest && extractUsername(token).equals(userDetails.getUsername());
	}

}
