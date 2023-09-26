package com.thakur.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private byte[] key = "jwtTokenkeyForMeMyNameIsAmanKumarThakurThisIsMyKey".getBytes();

	// retrieve usename from jwt token
	public String getUsernameFromJwtToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieving expiration date from jwt token
	public Date getExpirationDateFromJwtToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	// For retrieving any information from token we need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromJwtToken(token);
		return expirationDate.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String abcString = doGenerateToken(claims, userDetails.getUsername());
		return abcString;
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromJwtToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
