package com.revature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.controllers.UserController;
import com.revature.impl.UserServiceImpl;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.repositories.UserDao;
import com.revature.services.UserService;


@RunWith(SpringRunner.class)
public class UsersServiceImplIntegrationTest {
	
	@TestConfiguration
	static class UsersServiceImplTestContextConfiguration {
		
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}
	
	@Autowired
	private UserService userServ;
	
	@MockBean
	private UserDao userDao;
	
	@Before
	public void setUp() {
		StoredPassword sp = new StoredPassword();
		sp.setHashedPassword("password");
		User user =  new User("robert123",sp,"email");
		
		Mockito.when(userDao.findByUsername(user.getUsername())).thenReturn(user);
	}
	
	@Test
	public void whenValidName_thenUserShouldBeFound() {
		String name = "robert123";
		User found =  userServ.getUserByUsername(name);
		
		assertThat(found.getUsername()).isEqualTo(name);
	}
	
	

}
