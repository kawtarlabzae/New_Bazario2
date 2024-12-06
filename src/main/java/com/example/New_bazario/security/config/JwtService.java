package com.example.New_bazario.security.config;

import java.security.Key;

import java.util.*;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY="KR7EcpzWS6Zse/dvDcRps/WwSdhjN2xlmlyY7kma2zp0Yi7TmyHf0uInKm/64sl0191jFOMnd6Si9vqDACuhO/Q3FCpnOD/s5KbUe1/G8EIGr90qNDmZEYxNvdPDrPJxpJjD92nRCOZzovBuCvC5RJ/gWjzGcwKPjV0HNdeNoUJ9qbMkUYny61bRFt4yx7eI1lSPRJ96mFMhnIvXMEDaVd5YA9tULOr7ESsQXYzq+xd7Q2ZKc8/k8AC6pHHhcxOCsmp6LdcYu16yxXTTewRzy4Jo0DlcOKN7U/sc1xiFgZkNEMMHr3NEy46wZwNa4ezfQhH3/yFn6L91Ep7/ep88JmTIHUAzzojI0EeuXEd6J+0="; 

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token,Function<Claims, T> claimsResolver) {
		final Claims claims =extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
			) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username =extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		// TODO Auto-generated method stub
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
