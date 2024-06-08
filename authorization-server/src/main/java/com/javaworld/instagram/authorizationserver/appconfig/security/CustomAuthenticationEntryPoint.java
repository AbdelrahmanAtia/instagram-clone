package com.javaworld.instagram.authorizationserver.appconfig.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.http.HttpHeaders;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		System.out.println(">> starting commence function..");

		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

		PrintWriter writer = response.getWriter();

		writer.flush();
	}
}
