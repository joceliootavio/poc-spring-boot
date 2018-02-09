package com.ia.dell.springbootsample.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = auth.getName();
		String password = auth.getCredentials().toString();
		
		User user = userService.findByLoginAndPassword(username, password);
		if (user == null)
			throw new BadCredentialsException("Usuário inválido");
		
		UsernamePasswordAuthenticationToken auth2;
		if (Boolean.TRUE.equals(user.getAdmin()))
			auth2 = new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		else 
			auth2 = new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		
		SecurityContextHolder.getContext().setAuthentication(auth2);
		return auth2;
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}
}