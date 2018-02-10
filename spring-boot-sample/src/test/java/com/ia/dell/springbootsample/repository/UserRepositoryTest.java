package com.ia.dell.springbootsample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ia.dell.springbootsample.model.User;
import com.ia.dell.springbootsample.mother.UserMother;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration
public class UserRepositoryTest {
	
	@MockBean
	private ServletContext servletContext;
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private UserRepository userRepository;
 
    @Test
    public void shouldSaveUserSuccess() {
    	User result = userRepository.save(UserMother.create("jossa12345"));
    	entityManager.flush();
    	entityManager.clear();
    	
    	assertThat(entityManager.find(User.class, result.getId()));
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldSaveUserWithError() {
    	userRepository.save(new User());
    }    
    
    @Test
    public void shouldDeleteUserSuccess() {
    	Long userId = entityManager.persist(UserMother.create("jossa12345")).getId();
    	entityManager.flush();
    	entityManager.clear();
    	
    	userRepository.delete(userId);
    }
    
    @Test
    public void shouldReturnOneWhenFindByLoginAndPassword() {
    	entityManager.persist(UserMother.create("jossa"));
    	entityManager.flush();
    	entityManager.clear();
    	
    	assertThat(userRepository.findByLoginAndPassword("jossa", "jossa@123")).isNotNull();
    }     
    
    @Test
    public void shouldReturnNullWhenFindByLoginAndPasswordWithInvalidCredentials() {
    	entityManager.persist(UserMother.create("jossa"));
    	entityManager.flush();
    	entityManager.clear();
    	
    	assertThat(userRepository.findByLoginAndPassword("jossa", "jossa@12")).isNull();
    }    
    
    @Test
    public void shouldReturnOneAdminWhenFindAdmins() {
    	User admin = UserMother.create("jossa1");
    	admin.setAdmin(true);
		entityManager.persist(admin);
		entityManager.persist(UserMother.create("jossa2"));
    	entityManager.flush();
    	entityManager.clear();
    	
    	assertThat(userRepository.findByAdminTrue()).hasSize(1);;
    }    
    
    public ServletContext createServletContext() {
    	return servletContext;
    }
}