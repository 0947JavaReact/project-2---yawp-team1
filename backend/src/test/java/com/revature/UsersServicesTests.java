package com.revature;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;



import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.repositories.UserDao;
import com.revature.services.UserService;

public class UsersServicesTests {
	
	@InjectMocks
	public UserService userServ;
	
	@Mock
	public UserDao udao;


	@Before
	public void setUp() throws Exception {
		User mockUser = new User();
		StoredPassword mockPassHolder = new StoredPassword();
		mockUser.setUsername("robert");
		mockUser.setEmail("robert@gmail.com");
		mockPassHolder.setHashedPassword("password");
		mockUser.setPasswordHolder(mockPassHolder);
		when(udao.findByUsername(anyString())).then(invocation -> {
			String uname = invocation.getArgument(0);
			if(uname.equals("testuser")) {
				return mockUser;
			} else {
				return null;
			}
		});
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Points(2)
	public void test() {
		String username = "robert";
		String password = "password";
		User user = userServ.login(username, password);
		assertNotNull(user);
		verify(udao,times(1)).findByUsername(username);
	}

}
