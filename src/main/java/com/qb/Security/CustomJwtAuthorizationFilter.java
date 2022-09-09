package com.qb.Security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qb.Commons.Constants.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.SpringApplicationContext;
import com.qb.Commons.Exception.IdleTimeoutException;
import com.qb.Commons.Utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomJwtAuthorizationFilter extends BasicAuthenticationFilter {

	public CustomJwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		try {
			String header = req.getHeader(CommonConstants.INSTANCE.AUTHORIZATION_STRING);

			if (header == null || !header.startsWith(CommonConstants.INSTANCE.BEARER_STRING)) {
				chain.doFilter(req, res);
				return;
			}
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(authentication);

//			AuthServiceImpl authServiceImpl  = (AuthServiceImpl) SpringApplicationContext.getBean("authServiceImpl");
//			Object principal = authentication.getPrincipal();
//			String username = ((User) principal).getUsername();
//			authServiceImpl.saveRefreshTokenAndLastAccessTime(null, username);

			chain.doFilter(req, res);
		} catch (IdleTimeoutException ie){
			log.info("Exception while authenticating user: {}", ie.getMessage());
			forbiddenResponse(601, ie.getMessage(), res);
		}
		catch (ExpiredJwtException ex){
			log.info("Exception while authenticating user: {}", ex.getMessage());
			forbiddenResponse(602, ex.getMessage(), res);
		}
//		catch (MpinBlockedUserException exception) {
//			forbiddenResponse(TransitAPIConstants.API_MPIN_BLOCKED_USER_CODE, exception.getMessage(), res);
//		}
		catch (Exception e) {
			log.info("Exception while authenticating user: {}", e.getMessage());
			forbiddenResponse(603, e.getMessage(), res);
		}
	}
	
	private void forbiddenResponse(Integer statusCode, String message, HttpServletResponse res) throws IOException{
		res.setHeader("Content-Type","application/json");
		res.setStatus(HttpStatus.OK.value());
		ResponseEntity<String> resp = ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("commence : "+message);
		OutputStream out = res.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, resp);
		out.flush();
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws Exception {
		String header = request.getHeader(CommonConstants.INSTANCE.AUTHORIZATION_STRING);

		if (header != null) {
			String token = header.replace(CommonConstants.INSTANCE.BEARER_STRING, CommonConstants.INSTANCE.BLANK_STRING);
			JwtUtil jwtTokenUtil  = (JwtUtil) SpringApplicationContext.getBean("jwtUtil");
			if (!jwtTokenUtil.validateToken(token)){
				log.info("Returning null");
				return null;
			}
			log.info("Getting User");
			String user = jwtTokenUtil.getUsernameFromToken(token);
			log.info("Getting User :: {}", user);
			if (user != null) {
				CustomUserDetailsService userDetailsService  = (CustomUserDetailsService) SpringApplicationContext
						.getBean("customUserDetailsService");
				User userDetails = userDetailsService.loadUserByUsername(user);
				log.info("User :: {}", userDetails);
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
			return null;
		}
		return null;
	}

}
