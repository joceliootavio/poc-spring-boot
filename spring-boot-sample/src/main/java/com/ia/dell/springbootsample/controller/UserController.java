package com.ia.dell.springbootsample.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<User> index(@PageableDefault(page=0, size=50) Pageable pageable) {
		return this.userService.findAll(pageable);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public User get(@PathVariable Long userId) {
		return this.userService.findOne(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody User input) {
		User result = userService.save(input);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
	public ResponseEntity<?> update(@PathVariable Long userId, @RequestBody User input) {
		userService.save(input);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
	public ResponseEntity<?> delete(@PathVariable Long userId) {
		userService.delete(userId);
		return ResponseEntity.ok().build();
	}

}