package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.SearchReturnedZeroResultsException;
import com.revature.exceptions.UpdateFailedException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.StoredPassword;
import com.revature.models.User;
import com.revature.repositories.PasswordDao;
import com.revature.repositories.UserDao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("userServ")
public class UserService {
	private UserDao userDao;
	private PasswordDao passDao;

	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	
	 public String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }
    
    private boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
	 
	
	
	public void register(User u) {
		if (userDao.findByUsername(u.getUsername()) !=null) {
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
		if (userDao.findByUsername(username) ==null) {
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
		if(!userDao.findByUsername(user.getUsername()).equals(user)) {
			throw new UpdateFailedException();
		}
		return true;
		
	}
	
	public boolean addFollower(int user, int follower) {
		User u = getUserById(user);
		User f = getUserById(follower);
		u.getFollowers().add(f);
		userDao.save(u);
		if(!userDao.findByUsername(u.getUsername()).equals(u)) {
			throw new UpdateFailedException();
		}
		return true;
	}
	
	public boolean addFollowing(int user, int following) {
		User u = getUserById(user);
		User f = getUserById(following);
		u.getFollowing().add(f);
		userDao.save(u);
		if(!userDao.findByUsername(u.getUsername()).equals(u)) {
			throw new UpdateFailedException();
		}
		return true;
	}
	
	public boolean removeFollower(int user, int follower) {
		User u = getUserById(user);
		User f = getUserById(follower);
		u.getFollowers().remove(f);
		userDao.save(u);
		if(!userDao.findByUsername(u.getUsername()).equals(u)) {
			throw new UpdateFailedException();
		}
		return true;
	}
	public boolean removeFollowing(int user, int following) {
		User u = getUserById(user);
		User f = getUserById(following);
		u.getFollowing().remove(f);
		userDao.save(u);
		if(!userDao.findByUsername(u.getUsername()).equals(u)) { 
			throw new UpdateFailedException();
		}
		return true;
	}
	
	public Set<User> getUserFollowers(int id) {
		User user = userDao.findById(id).get();
		
		System.out.println("Found user: " + user);
		return user.getFollowers();
	}
	
	public Set<User> getUserFollowing(int id) {
		User user = userDao.findById(id).get();
		return user.getFollowing();
	}
	
	public User login(String username, String password) {
		User user = new User();
		user = userDao.findByUsername(username);
		
		if (userDao.findByUsername(username) ==null) {
			throw new UsernameDoesNotExistException();
		}
		
		if(!checkPassword(password, user.getPasswordHolder().getHashedPassword())) {
			 throw new InvalidCredentialsException();
		}
		return user;
	}
}
