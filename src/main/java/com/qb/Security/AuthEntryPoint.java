package com.qb.Security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException {
		res.setHeader("Content-Type","application/json");
        res.setStatus(HttpStatus.OK.value());
        ResponseEntity<String> resp = ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("Commence : "+ex.getMessage());
//        BaseResponse<String> resp = new BaseResponse<>(HttpStatus.FORBIDDEN.value(),ex.getMessage(),"");
        OutputStream out = res.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, resp);
        out.flush();
	}

}
