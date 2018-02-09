package com.ia.dell.springbootsample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.dell.springbootsample.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByLogin(String login);
	public User findByLoginAndPassword(String login, String password);
	public List<User> findByAdminTrue();	
}
