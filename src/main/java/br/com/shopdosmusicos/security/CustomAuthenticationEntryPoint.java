package br.com.shopdosmusicos.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	 @Override
	 public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
	     res.setContentType("application/json;charset=UTF-8");
	     res.setStatus(HttpStatus.UNAUTHORIZED.value());
	     ObjectMapper mapper = new ObjectMapper();
	     res.getWriter().write(mapper.writeValueAsString(new Error("Usuário e/ou senha inválidos")
	     ));
	 }
}
