package com.ia.dell.springbootsample.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.service.UserService;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationProvider authProvider;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Login input, HttpServletRequest request) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword(), Collections.emptyList());
			token.setDetails(new WebAuthenticationDetails(request));
			authProvider.authenticate(token);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			invalidateSession(request);
			throw ex;
		}
	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpServletRequest request) {
		invalidateSession(request);
		return ResponseEntity.ok().build();
	}

	private void invalidateSession(HttpServletRequest request) {
		if (!request.getSession().isNew()) {
			request.getSession().invalidate();
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}	

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/change-password", method = RequestMethod.PATCH)
	public ResponseEntity<?> changePassword(@RequestBody Login input) {
		User result = userService.findByLogin(input.getUsername());
		if (result == null)
			return ResponseEntity.unprocessableEntity().build();

		result.setPassword(input.getPassword());
		userService.save(result);
		return ResponseEntity.ok().build();
	}
}

@Getter
@Setter
class Login {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
}