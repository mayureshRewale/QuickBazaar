package com.qb.Commons.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.qb.Commons.Exception.IdleTimeoutException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	private String secret = "2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a";
	private long jwtExpirationInSec = 100000;
	private long refreshExpirationDateInSec = 100000;
	private long idleSessionTimeOut = 100000;
	
	@Autowired
	RedisClientUtils redisClient;

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isAccessToken", true);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		long currentTimeInMillis = CommonUtils.getCurrentTimeMillis();
		String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(currentTimeInMillis))
				.setExpiration(new Date(currentTimeInMillis + jwtExpirationInSec*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
			long exp_time = CommonUtils.getCurrentTimeSec()+ idleSessionTimeOut;
			String redisKey = subject+":"+token;
//			redisClient.deletePattern(subject+":*");
			redisClient.setValue(redisKey, String.valueOf(exp_time), idleSessionTimeOut);
		return token;

	}
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
		long currentTimeInMillis = CommonUtils.getCurrentTimeMillis();
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(currentTimeInMillis))
				.setExpiration(new Date(currentTimeInMillis + refreshExpirationDateInSec*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
	public boolean validateToken(String authToken) {
		try {
			String userName = "";
			try{
				Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
				userName = claims.getBody().getSubject();
			} catch (ExpiredJwtException ex){
				userName =ex.getClaims().getSubject();
			}
			String redisKey= userName+":"+authToken;
			String res = redisClient.getValue(redisKey);
			if (res==null){
				throw new IdleTimeoutException("Session TimeOut");
			}
//			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			long exp_time = CommonUtils.getCurrentTimeSec()+ idleSessionTimeOut;
			redisClient.setValue(redisKey, String.valueOf(exp_time), idleSessionTimeOut);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | BadCredentialsException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		}
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.getSubject();

	}

	/**
	 * generate full jwt token (access+refresh) in autherization header
	 * @param username
	 * @return
	 */
	public String getFullAutherizationToken(String username) {
		Map<String, Object> accessClaims = new HashMap<>();
		accessClaims.put("isAccessToken", true);
		String accessToken = doGenerateToken(accessClaims, username);
		Map<String, Object> refreshClaims = new HashMap<>();
		refreshClaims.put("isRefreshToken", true);
		String refreshToken = doGenerateRefreshToken(refreshClaims, username);
		return "Bearer "+accessToken+ "|Bearer "+refreshToken;
	}

}
