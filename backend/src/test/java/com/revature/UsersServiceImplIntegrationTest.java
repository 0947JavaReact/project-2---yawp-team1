package com.revature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.revature.exceptions.InvalidCredentialsException;
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

	// test the specific implementation files to not affect our service package
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

	// wired services
	@Autowired
	private UserService userServ;
	@Autowired
	private YawpService yawpServ;
	// DAO beans mocked for testing
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

	// global test objects for users
	private StoredPassword sp;
	private StoredPassword sp1;
	private User user;
	private User user1;
	private Set<User> sUser1;
	private Set<User> sUser2;
	// global test objects for yawps
	private Yawp yawp1;
	private Yawp yawp2;
	private List<Yawp> list1;
	private List<Yawp> list2;

	@Before
	// the mockitos will specifically return what we declare here
	public void setUp() {
		////////////////////////
		/// Setup for Users
		////////////////////////
		
		// create a user list, create a password, create a user, add user to list
		List<User> uList = new ArrayList<>();
		sp = new StoredPassword();
		sp.setHashedPassword("password");
		user = new User("robert123", sp, "email");
		uList.add(user);

		// when finding user by username, return user
		Mockito.when(userDao.findByUsername(user.getUsername())).thenReturn(user);

		// create sp3, create user4, add user4 to uList
		StoredPassword sp3 = new StoredPassword();
		sp3.setHashedPassword("testPass");
		User user4 = new User("testUser", sp3, "test@gmail.com");
		uList.add(user4);
		// update sp3, create user5, add user5 to uList
		sp3.setHashedPassword("newPass");
		User user5 = new User("newUser", sp3, "new@gmail.com");
		uList.add(user5);
		// when finding all users, return uList
		Mockito.when(userDao.findAll()).thenReturn(uList);

		// update sp1, create sp2, update user1, create user2
		sp1 = new StoredPassword("password");
		StoredPassword sp2 = new StoredPassword("password2");
		user1 = new User(1, "robert1", sp1, "robert.c9588@gmail.com", "bio1", "pic1");
		User user2 = new User(2, "robert2", sp2, "rob2@email.com", "bio2", "pic2");
		// when adding user1 to the User Table, return user1
		Mockito.when(userDao.save(user1)).thenReturn(user1);
		// when adding sp1 to the Password Table, return sp1
		Mockito.when(passDao.save(sp1)).thenReturn(sp1);
		// when finding user1 by their email, return user1
		Mockito.when(userDao.findByEmail(user1.getEmail())).thenReturn(user1);

		// when finding user1 by their id, return user1
		Optional<User> op1 = Optional.ofNullable(user1);
		Mockito.when(userDao.findById(user1.getUserId())).thenReturn(op1);
		// when finding user2 by their id, return user2
		Optional<User> op2 = Optional.ofNullable(user2);
		Mockito.when(userDao.findById(user2.getUserId())).thenReturn(op2);

		// when finding user1 by their username, return user1
		Mockito.when(userDao.findByUsername(user1.getUsername())).thenReturn(user1);

		// create search string
		String str = "r";
		// when finding a user with a username that contains str, return uList
		Mockito.when(userDao.findByUsernameContaining(str)).thenReturn(uList);
		// create uList2, add second user from uList, add third user from uList
		List<User> uList2 = new ArrayList<>();
		uList2.add(uList.get(1));
		uList2.add(uList.get(2));
		// when finding a user with a username that contains "user", return uList2
		Mockito.when(userDao.findByUsernameContaining("user")).thenReturn(uList2);

		// create sFollowers relationship, create sFollowing relationship
		StoredFollowers sFollowers = new StoredFollowers(1, 2);
		StoredFollowing sFollowing = new StoredFollowing(2, 1);
		// when adding sFollowers to database, return sFollowers
		Mockito.when(followerHDao.save(sFollowers)).thenReturn(sFollowers);
		// when adding sFollowing to database, return sFollowing
		Mockito.when(followingHDao.save(sFollowing)).thenReturn(sFollowing);

		// create followingList, add sFollowing to list
		List<StoredFollowing> followingList = new ArrayList<>();
		followingList.add(sFollowing);
		// create followersList, add sFollowers to list
		List<StoredFollowers> followersList = new ArrayList<>();
		followersList.add(sFollowers);

		// when finding all followers from database by user id 1, return followersList
		Mockito.when(followerHDao.findAllByUserId(1)).thenReturn(followersList);
		// when finding all following from database by user id 2, return followingList
		Mockito.when(followingHDao.findAllByUserId(2)).thenReturn(followingList);

		////////////////////////
		/// Setup for Yawps
		////////////////////////

		// instantiate sUser1 and sUser2
		sUser1 = new HashSet<>();
		sUser2 = new HashSet<>();
		// add user2 to the set sUser1
		sUser1.add(user2);
		// add user1 to the set sUser2
		sUser2.add(user1);

		// instantiate yawp1 with a yawp created by user1
		yawp1 = new Yawp(1, "This is a Yawp Message", user1.getUserId(), user1.getUsername(), user1.getPicUrl(),
				LocalDateTime.now(), sUser1);
		// instantiate yawp2 with a yawp created by user2
		yawp2 = new Yawp(2, "This is a Yawp Message2", user2.getUserId(), user2.getUsername(), user2.getPicUrl(),
				LocalDateTime.now(), sUser2);

		// add yawp1 to yawp list1
		list1 = new ArrayList<Yawp>();
		list1.add(yawp1);
		// add yawp2 to yawp list2
		list2 = new ArrayList<Yawp>();
		list2.add(yawp2);
		// when finding yawps by user1's id, return list1
		Mockito.when(ydao.findByAuthorId(user1.getUserId())).thenReturn(list1);
		// when finding yawps by user2's id, return list2
		Mockito.when(ydao.findByAuthorId(user2.getUserId())).thenReturn(list2);
		
		// add yawp2 to list1
		list1.addAll(list2);
		// when finding all yawps, return list1
		Mockito.when(ydao.findAll()).thenReturn(list1);
		// when adding yawp1 to the database, return yawp1
		Mockito.when(ydao.save(yawp1)).thenReturn(yawp1);

		// when finding yawp1 by its id, return yawp1
		Optional<Yawp> opyawp = Optional.ofNullable(yawp1);
		Mockito.when(ydao.findById(1)).thenReturn(opyawp);
	}

	////////////////////////
	/// Yawp Tests
	////////////////////////
	@Test
	public void whenCallingGetYawpsByFollowers_thenReturnFollowerYawps() {
		// get a list of yawps looking at followers of user with id of 1
		List<Yawp> y1List = yawpServ.getYawpsByFollowers(1);
		// make sure yawp2 was grabbed
		assertThat(y1List.get(0)).isEqualTo(yawp2);
	}

	@Test
	public void whenCallingGetYawpsByFollwing_thenReturnFollowerYawps() {
		// get a list of yawps looking at following of user with id of 1
		List<Yawp> y1List = yawpServ.getYawpsByFollowing(1);
		// make sure no yawps are in the list
		assertTrue(y1List.isEmpty());
	}

	@Test
	public void whenCallingGetYawp_thenReturnOneYawp() {
		// get a yawp with id of 1
		Yawp found = yawpServ.getYawp(1);
		// make sure yawp1 was grabbed
		assertThat(found).isEqualTo(yawp1);
	}

	@Test
	public void whenCallingAll_thenYawpAllYawpsShouldBeFound() {
		// create passwords sp1 and sp2, create users user1 and user2
		StoredPassword sp1 = new StoredPassword("password");
		StoredPassword sp2 = new StoredPassword("password2");
		User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
		User user2 = new User(2, "robert2", sp2, "rob2@email.com", "bio2", "pic2");
		// create sets sUser1 and sUser2
		Set<User> sUser1 = new HashSet<>();
		Set<User> sUser2 = new HashSet<>();
		// add user2 to the set sUser1
		sUser1.add(user2);
		// add user1 to the set sUser2
		sUser2.add(user1);
		// user1 creates yawp1
		Yawp yawp1 = new Yawp(1, "This is a Yawp Message", user1.getUserId(), user1.getUsername(), user1.getPicUrl(),
				LocalDateTime.now(), sUser1);
		// user2 creates yawp2
		Yawp yawp2 = new Yawp(2, "This is a Yawp Message2", user2.getUserId(), user2.getUsername(), user2.getPicUrl(),
				LocalDateTime.now(), sUser2);
		// create a yawp list1, add yawp1 and yawp2 to the list
		List<Yawp> list1 = new ArrayList<Yawp>();
		list1.add(yawp1);
		list1.add(yawp2);
		// get a list of all yawps
		List<Yawp> found = yawpServ.getAllYawps();
		// make sure both lists have only two yawps
		assertThat(found.size()).isEqualTo(list1.size());
	}

	////////////////////////
	/// User Tests
	////////////////////////
	@Test
	public void whenValidName_thenUserShouldBeFound() {
		// create username name
		String name = "robert123";
		// find a user by name string
		User found = userServ.getUserByUsername(name);
		// make sure a user with the same username was found
		assertThat(found.getUsername()).isEqualTo(name);
	}

	@Test
	public void whenValidUser_thenAllUsersShouldBeFound() {
		// create uList, assign all the users to the list
		List<User> uList = new ArrayList<>();
		uList = userServ.getAllUsers();
		// make sure there are only 3 users in the list
		assertThat(uList.size()).isEqualTo(3);
	}

	@Test
	public void whenPwordGenerated_thenPwordShouldNotBeEmpty() {
		// create password tempPass, assign a new generated password
		String tempPass = "";
		tempPass = userServ.generatePassayPassword();
		// make sure a password was actually generated
		assertThat(tempPass).isNotEqualTo("");
	}

	@Test
	public void whenFindingUserById_thenReturnUser() {
		// create password sp1, create user1
		StoredPassword sp1 = new StoredPassword("password");
		User user1 = new User(1, "robert1", sp1, "robert.c9588@gmail.com", "bio1", "pic1");
		// get user1 by their id, assign to user2
		User user2 = userServ.getUserById(user1.getUserId());
		// make sure attributes are the same
		assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
		assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
		assertThat(user1.getBio()).isEqualTo(user2.getBio());
	}

	@Test
	public void whenFindingUserByUsername_thenReturnUser() {
		// create password sp1, create user1
		StoredPassword sp1 = new StoredPassword("password");
		User user1 = new User(1, "robert1", sp1, "robert.c9588@gmail.com", "bio1", "pic1");
		// get user1 by their username, assign to user2
		User user2 = userServ.getUserByUsername(user1.getUsername());
		// make sure attributes are the same
		assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
		assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
		assertThat(user1.getBio()).isEqualTo(user2.getBio());
	}

	@Test
	public void whenSearchingByUsername_thenReturnUser() {
		// create user list uList, assign all users to the list
		List<User> uList = new ArrayList<>();
		uList = userServ.getAllUsers();
		// search the database for users with a username containing "r", assign to searchList
		List<User> searchList = userServ.searchByUsername("r");
		// make sure all users are found because all usernames have an 'r' character
		assertThat(uList.size()).isEqualTo(searchList.size());
	}

	@Test
	public void whenUpdatingUser_thenReturnUser() {
		// create password sp1, create user1
		StoredPassword sp1 = new StoredPassword("password");
		User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
		// change user1's bio attribute
		user1.setBio("different bio");
		// update the database
		boolean updated = userServ.updateUser(user1);
		// make sure the update was successful
		assertThat(updated).isEqualTo(true);
	}

	@Test
	public void whenAddingOrRemovingFollower_thenListUpdated() {
		// create password sp1, create user1, update sp1, create user2
		StoredPassword sp1 = new StoredPassword("password");
		User user1 = new User(1, "robert1", sp1, "rob1@email.com", "bio1", "pic1");
		sp1.setHashedPassword("newPass");
		User user2 = new User(2, "robert2", sp1, "rob2@email.com", "bio2", "pic2");
		// create followingList, add user1 to the list
		List<User> followingList = new ArrayList<>();
		followingList.add(user1);
		// create followersList, add user2 to the list
		List<User> followersList = new ArrayList<>();
		followersList.add(user2);
		
		// make sure the follower relationship was established successfully
		assertThat(userServ.addFollower(1, 2)).isEqualTo(true);
		// create actualFollowers, assign followers for user id of 1
		List<User> actualFollowers = userServ.getUserFollowers(1);
		// make sure the same follower is in both lists
		assertThat(followersList.get(0).getUsername()).isEqualTo(actualFollowers.get(0).getUsername());
		// make sure the follower relationship was removed successfully
		assertThat(userServ.removeFollower(1, 2)).isEqualTo(true);
		
		// make sure the following relationship was established successfully
		assertThat(userServ.addFollowing(2, 1)).isEqualTo(true);
		// create actualFollowing, assign following for user id of 2
		List<User> actualFollowing = userServ.getUserFollowing(2);
		// make sure the same following is in both lists
		assertThat(followingList.get(0).getUsername()).isEqualTo(actualFollowing.get(0).getUsername());
		// make sure the following relationship was removed successfully
		assertThat(userServ.removeFollowing(2, 1)).isEqualTo(true);
	}

	@Test
	public void whenLoggingIn_thenReturnUser() {
		// create password tempPass, encrypt tempPass and assign to hashPass
		String tempPass = "password";
		String hashPass = userServ.hashPassword(tempPass);
		// update password of user
		user.getPasswordHolder().setHashedPassword(hashPass);
		// make sure the password was correctly hashed
		assertThat(userServ.checkPassword(tempPass, hashPass)).isEqualTo(true);
		// find user when logging in by their username and password
		User found = userServ.login(user.getUsername(), tempPass);
		// make sure that user was found
		assertThat(found.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	public void whenSendingEmail_thenReturnVoid() {
		// send a reset email to user1's email address
		userServ.sendResetEmail(user1.getEmail());
		// make sure an exception was thrown because user1 reset their password
		assertThrows(InvalidCredentialsException.class,	()->userServ.resetPassword(user1.getEmail(), "newpassword!!!", "newpass"));
	}
}
