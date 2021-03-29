package com.revature;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.impl.UserServiceImpl;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.repositories.FollowerHolderDao;
import com.revature.repositories.FollowingHolderDao;
import com.revature.repositories.PasswordDao;
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
	@MockBean
	private FollowerHolderDao followerHDao;
	@MockBean
	private FollowingHolderDao followingHDao;
	@MockBean
	private PasswordDao passDao;
	
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
		User found = userServ.getUserByUsername(name);
		
		assertThat(found.getUsername()).isEqualTo(name);
	}
	
	@Test
	public void whenValidUser_thenAllUsersShouldBeFound() {
		List<User> uList = new ArrayList<>();
		uList = userServ.getAllUsers();
		
		assertThat(uList.size()).isEqualTo(1);
	}

	@Test
	public void whenPwordGenerated_thenPwordShouldBeSame() {
		String tempPass = "";
		tempPass = userServ.generatePassayPassword();
		
		assertThat(tempPass).isNotEqualTo("");
	}
	
	@Test
	public void whenPwordHashed_thenPwordHashIsSame() {
		String tempPass = "password";
		String hashPass = userServ.hashPassword(tempPass);
		User user = userServ.getUserByUsername("robert123");
		String userHashPass = userServ.hashPassword(user.getPasswordHolder().getHashedPassword());
		
		assertThat(hashPass).isEqualTo(userHashPass);
	}
	
}
