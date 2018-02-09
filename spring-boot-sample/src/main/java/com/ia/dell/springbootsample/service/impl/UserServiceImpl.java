package com.ia.dell.springbootsample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.repository.UserRepository;
import com.ia.dell.springbootsample.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findByLoginAndPassword(String login, String password) {
		return userRepository.findByLoginAndPassword(login, password);
	}

	@Override
	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAdmins() {
		return userRepository.findByAdminTrue();
	}

	@Override
	public User findOne(Long userId) {
		return userRepository.findOne(userId);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public void delete(Long userId) {
		userRepository.delete(userId);
	}
}
