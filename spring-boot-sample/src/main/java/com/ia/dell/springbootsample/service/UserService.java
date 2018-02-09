package com.ia.dell.springbootsample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ia.dell.springbootsample.model.User;

public interface UserService {
	User findByLogin(String login);
	User findByLoginAndPassword(String login, String password);
	User findOne(Long userId);
	List<User> findAdmins();
	Page<User> findAll(Pageable pageable);	
	User save(User user);
	void delete(Long userId);
}
