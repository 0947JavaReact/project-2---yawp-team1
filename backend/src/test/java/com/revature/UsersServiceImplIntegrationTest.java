package com.revature;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.revature.impl.YawpServiceImpl;
import com.revature.models.StoredFollowers;
import com.revature.models.StoredFollowing;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.repositories.FollowerHolderDao;
import com.revature.repositories.FollowingHolderDao;
import com.revature.repositories.PasswordDao;
import com.revature.repositories.UserDao;
import com.revature.repositories.YawpDao;
import com.revature.services.UserService;
import com.revature.services.YawpService;


@RunWith(SpringRunner.class)
public class UsersServiceImplIntegrationTest {
	
	@TestConfiguration
	static class UsersServiceImplTestContextConfiguration {
		
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}
	@TestConfiguration
    static class YawpServiceImplTestContextConfiguration {
        
        @Bean
        public YawpService yawpService() {
            return new YawpServiceImpl();
        }
    }
	
	@Autowired
	private UserService userServ;
	@Autowired
	private YawpService yawpServ;
	
	@MockBean
	private UserDao userDao;
	@MockBean
	private FollowerHolderDao followerHDao;
	@MockBean
	private FollowingHolderDao followingHDao;
	@MockBean
	private PasswordDao passDao;
	@MockBean
	private YawpDao ydao;
	
	@Before
	public void setUp() {
		List<User> uList = new ArrayList<>();
		StoredPassword sp = new StoredPassword();
		sp.setHashedPassword("password");
		User user =  new User("robert123",sp,"email");
		uList.add(user);
		
		Mockito.when(userDao.findByUsername(user.getUsername())).thenReturn(user);
		
		StoredPassword sp3 = new StoredPassword();
		sp.setHashedPassword("testPass");
		User user4 = new User("testUser", sp3, "test@gmail.com");
		uList.add(user4);
		sp.setHashedPassword("newPass");
		User user5 = new User("newUser", sp3, "new@gmail.com");
		uList.add(user5);
		Mockito.when(userDao.findAll()).thenReturn(uList);
		
		StoredPassword sp1 = new StoredPassword("password");
        StoredPassword sp2 = new StoredPassword("password2");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        User user2 = new User(2, "robert2", sp2, "rob2@email.com", "bio2", "pic2");
        
        Mockito.when(userDao.save(user1)).thenReturn(user1);
        
        Optional<User> op1 = Optional.ofNullable(user1);
        Mockito.when(userDao.findById(user1.getUserId())).thenReturn(op1);
        Optional<User> op2 = Optional.ofNullable(user2);
        Mockito.when(userDao.findById(user2.getUserId())).thenReturn(op2);
        
        Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(user1);
        
        String str = "r";
        Mockito.when(userDao.findByUsernameContaining(str)).thenReturn(uList);
        //uList.remove(0);
        List<User> uList2 = new ArrayList<>();
        uList2.add(uList.get(1));
        uList2.add(uList.get(2));
        Mockito.when(userDao.findByUsernameContaining("user")).thenReturn(uList2);
        
        ///
        StoredFollowers sFollowers = new StoredFollowers(1,2);
        StoredFollowing sFollowing = new StoredFollowing(2,1);
        
        Mockito.when(followerHDao.save(sFollowers)).thenReturn(sFollowers);
        Mockito.when(followingHDao.save(sFollowing)).thenReturn(sFollowing);
        
        StoredPassword spOne = new StoredPassword("password");
        User userOne = new User(1, "robert1", spOne, "rob1@email.com", "bio1", "pic1");
		sp1.setHashedPassword("newPass");
		User userTwo = new User(2, "robert2", spOne, "rob2@email.com", "bio2", "pic2");
		List<StoredFollowing> followingList = new ArrayList<>();
		followingList.add(sFollowing);
        List<StoredFollowers> followersList = new ArrayList<>();
        followersList.add(sFollowers);
        
        Mockito.when(followerHDao.findAllByUserId(1)).thenReturn(followersList);
        Mockito.when(followingHDao.findAllByUserId(2)).thenReturn(followingList);
        ///
        
        Set<User> sUser1 = new HashSet<>();
        Set<User> sUser2 = new HashSet<>();
        
        sUser1.add(user2);
        sUser2.add(user1);
        
        Yawp yawp1 = new Yawp(1, "This is a Yawp Message", user1.getUserId(), user1.getUsername(), user1.getPicUrl(), LocalDateTime.now(), sUser1);
        Yawp yawp2 = new Yawp(2, "This is a Yawp Message2", user2.getUserId(), user2.getUsername(), user2.getPicUrl(), LocalDateTime.now(), sUser2);

        List<Yawp> list1 = new ArrayList<Yawp>();
        list1.add(yawp1);
        List<Yawp> list2 = new ArrayList<Yawp>();
        list1.add(yawp2);
        
        Mockito.when(ydao.findByAuthorId(user1.getUserId())).thenReturn(list1);
        Mockito.when(ydao.findByAuthorId(user2.getUserId())).thenReturn(list2);
        
        list1.addAll(list2);
        //Collections.sort(list1,Comparator.reverseOrder());
        
        Mockito.when(ydao.findAll()).thenReturn(list1);
        
        Mockito.when(ydao.save(yawp1)).thenReturn(yawp1);
	}
	
	@Test
	public void whenValidName_thenUserShouldBeFound() {
		String name = "robert123";
		User found = userServ.getUserByUsername(name);
		
		assertThat(found.getUsername()).isEqualTo(name);
	}
	
	@Test
    public void whenCallingAll_thenYawpAllYawpsShouldBeFound() {
        StoredPassword sp1 = new StoredPassword("password");
        StoredPassword sp2 = new StoredPassword("password2");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        User user2 = new User(2, "robert2", sp2, "rob2@email.com", "bio2", "pic2");
        
        Set<User> sUser1 = new HashSet<>();
        Set<User> sUser2 = new HashSet<>();
        
        sUser1.add(user2);
        sUser2.add(user1);
        
        Yawp yawp1 = new Yawp(1, "This is a Yawp Message", user1.getUserId(), user1.getUsername(), user1.getPicUrl(), LocalDateTime.now(), sUser1);
        Yawp yawp2 = new Yawp(2, "This is a Yawp Message2", user2.getUserId(), user2.getUsername(), user2.getPicUrl(), LocalDateTime.now(), sUser2);

        List<Yawp> list1 = new ArrayList<Yawp>();
        list1.add(yawp1);
        //List<Yawp> list2 = new ArrayList<Yawp>();
        list1.add(yawp2);
       // Collections.sort(list1);

        
        List<Yawp> found = yawpServ.getAllYawps();
        assertThat(found.size()).isEqualTo(list1.size());
    }
	
	@Test
	public void whenValidUser_thenAllUsersShouldBeFound() {
		List<User> uList = new ArrayList<>();
		uList  = userServ.getAllUsers();
		
		assertThat(uList.size()).isEqualTo(3);
	}

	@Test
	public void whenPwordGenerated_thenPwordShouldNotBeEmpty() {
		String tempPass = "";
		tempPass = userServ.generatePassayPassword();
		
		assertThat(tempPass).isNotEqualTo("");
	}
	/*
	@Test
	public void whenPwordHashed_thenPwordHashIsSame() {
		String tempPass = "password";
		String hashPass = userServ.hashPassword(tempPass);
		User user = userServ.getUserByUsername("robert123");
		String userHashPass = userServ.hashPassword(user.getPasswordHolder().getHashedPassword());
		
		//assertThat(hashPass).isEqualTo(userHashPass);
		//assertTrue(userServ.)
	} */
	/*
	@Test
	public void whenUserRegistered_thenUserExists() {
		StoredPassword sp1 = new StoredPassword("password");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        User user2 = userServ.register(user1);
        assertThat(user1).isEqualTo(user2);
	} */
	
	@Test
	public void whenFindingUserById_thenReturnUser() {
		StoredPassword sp1 = new StoredPassword("password");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        User user2 = userServ.getUserById(user1.getUserId());
        
        assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getBio()).isEqualTo(user2.getBio());
	}
	
	@Test
	public void whenFindingUserByUsername_thenReturnUser() {
		StoredPassword sp1 = new StoredPassword("password");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        User user2 = userServ.getUserByUsername(user1.getUsername());
        
        assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getBio()).isEqualTo(user2.getBio());
	}
	
	@Test
	public void whenSearchingByUsername_thenReturnUser() {
		List<User> uList = new ArrayList<>();
		uList = userServ.getAllUsers();
		List<User> searchList = userServ.searchByUsername("r");
		
		assertThat(uList.size()).isEqualTo(searchList.size());
	}
	
	@Test
	public void whenUpdatingUser_thenReturnUser() {
		StoredPassword sp1 = new StoredPassword("password");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        user1.setBio("different bio");
        boolean updated = userServ.updateUser(user1);
        
        assertThat(updated).isEqualTo(true);
	}
	
	@Test
	public void whenAddingOrRemovingFollower_thenListUpdated() {
		StoredPassword sp1 = new StoredPassword("password");
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
		sp1.setHashedPassword("newPass");
		User user2 = new User(2, "robert2", sp1, "rob2@email.com", "bio2", "pic2");
		
		List<User> followingList = new ArrayList<>();
		followingList.add(user1);
        List<User> followersList = new ArrayList<>();
        followersList.add(user2);
        
		assertThat(userServ.addFollower(1, 2)).isEqualTo(true);
        List<User> actualFollowers = userServ.getUserFollowers(1);
		assertThat(followersList.get(0).getUsername()).isEqualTo(actualFollowers.get(0).getUsername());
		assertThat(userServ.removeFollower(1, 2)).isEqualTo(true);
		
		assertThat(userServ.addFollowing(2, 1)).isEqualTo(true);
        List<User> actualFollowing = userServ.getUserFollowing(2);
		assertThat(followingList.get(0).getUsername()).isEqualTo(actualFollowing.get(0).getUsername());
		assertThat(userServ.removeFollowing(2, 1)).isEqualTo(true);
	}
	/*
	@Test
	public void whenLoggingIn_thenReturnUser() {
		StoredPassword sp1 = new StoredPassword("password");
		String hashed = userServ.hashPassword(sp1.getHashedPassword());
		sp1.setHashedPassword(hashed);
        User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
        
        assertThat(user1.getUsername()).isEqualTo(userServ.login(user1.getUsername(), user1.getPasswordHolder().getHashedPassword()));
	}*/
}
