package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.SearchReturnedZeroResultsException;
import com.revature.exceptions.UpdateFailedException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.StoredFollowers;
import com.revature.models.StoredFollowing;
import com.revature.models.User;
import com.revature.repositories.FollowerHolderDao;
import com.revature.repositories.FollowingHolderDao;
import com.revature.repositories.PasswordDao;
import com.revature.repositories.UserDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("userServ")
public class UserService {
	private UserDao userDao;
	private FollowerHolderDao followerHDao;
	private FollowingHolderDao followingHDao;
	private PasswordDao passDao;
	// private UserRepository userRepo;

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return (hashed_password);
	}

	private boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if (null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return (password_verified);
	}

	public void register(User u) {
		if (userDao.findByUsername(u.getUsername()) != null) {
			throw new UserAlreadyExistsException();
		}

		passDao.save(u.getPasswordHolder());
		userDao.save(u);
	}

	public User getUserById(int id) {
		Optional<User> oUser = userDao.findById(id);

		return oUser.get();
	}

	public User getUserByUsername(String username) {
		User user = userDao.findByUsername(username);
		System.out.println("User: " + username);
		System.out.println(user);
		if (user == null) {
			throw new UsernameDoesNotExistException();
		}
		return userDao.findByUsername(username);
	}

	public List<User> searchByUsername(String str) {
		List<User> uList = new ArrayList<>();
		uList = userDao.findByUsernameContaining(str);
		if (uList.isEmpty()) {
			throw new SearchReturnedZeroResultsException();
		}
		return uList;
	}

	public boolean updateUser(User user) {
		userDao.save(user);
		if (!userDao.findByUsername(user.getUsername()).equals(user)) {
			throw new UpdateFailedException();
		}
		return true;

	}

	public boolean addFollower(int user, int follower) {
		StoredFollowers sf = new StoredFollowers();
		sf.setUserId(user);
		sf.setFollowerId(follower);
		followerHDao.save(sf);
		return true;
	}

	public boolean addFollowing(int user, int following) {
		StoredFollowing sf = new StoredFollowing();
		sf.setUserId(user);
		sf.setFollowingId(following);
		followingHDao.save(sf);
		return true;
	}

	public boolean removeFollower(int user, int follower) {
		List<StoredFollowers> sfList = followerHDao.findAllByUserId(user);
		System.out.println("in remove follower list: "+ sfList);
		for (StoredFollowers sf : sfList) {
			if (sf.getFollowerId() == follower) {
				System.out.println(sf);
				followerHDao.delete(sf);
			}
		}
		return true;
	}

	public boolean removeFollowing(int user, int following) {
		System.out.println("user id: " + user + " /nfollowing: "+ following);
		List<StoredFollowing> sfList = followingHDao.findAllByUserId(user);
		System.out.println("in remove following list: "+ sfList);
		for (StoredFollowing sf : sfList) {
			if (sf.getFollowingId() == following) {
				System.out.println(sf);
				followingHDao.delete(sf);
			}
		}
		return true;
	}

	public List<User> getUserFollowers(int id) {

		List<StoredFollowers> sUsers = followerHDao.findAllByUserId(id);
		System.out.println(sUsers);
		List<User> userList = new ArrayList<>();
		for (StoredFollowers i : sUsers) {
			userList.add(getUserById(i.getFollowerId()));
		}

		return userList;
	}

	public List<User> getUserFollowing(int id) {
		List<StoredFollowing> sUsers = followingHDao.findAllByUserId(id);
		System.out.println(sUsers);
		List<User> userList = new ArrayList<>();
		for (StoredFollowing i : sUsers) {
			userList.add(getUserById(i.getFollowingId()));
		}
		return userList;
	}

	public User login(String username, String password) {
		User user = new User();
		user = userDao.findByUsername(username);

		if (userDao.findByUsername(username) == null) {
			throw new UsernameDoesNotExistException();
		}

		if (!checkPassword(password, user.getPasswordHolder().getHashedPassword())) {
			throw new InvalidCredentialsException();
		}
		return user;
	}
}
