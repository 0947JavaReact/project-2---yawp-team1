package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.mindrot.jbcrypt.BCrypt;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.EmailDoesNotExistException;
import com.revature.exceptions.EmailNotSentException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.SearchReturnedZeroResultsException;
import com.revature.exceptions.UpdateFailedException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UsernameDoesNotExistException;
import com.revature.models.Email;
import com.revature.models.StoredFollowers;
import com.revature.models.StoredFollowing;
import com.revature.models.StoredPassword;
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

	// get a list of all users from the database
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	// create a generated password
	public String generatePassayPassword() {
		PasswordGenerator gen = new PasswordGenerator();
		// establish lower case characters rule
		CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
		CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
		lowerCaseRule.setNumberOfCharacters(2);
		// establish upper case characters rule
		CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
		CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
		upperCaseRule.setNumberOfCharacters(2);
		// establish digit characters rule
		CharacterData digitChars = EnglishCharacterData.Digit;
		CharacterRule digitRule = new CharacterRule(digitChars);
		digitRule.setNumberOfCharacters(2);
		// establish special characters rule
		CharacterData specialChars = new CharacterData() {
			public String getErrorCode() {
				return "ERROR";
			}
			public String getCharacters() {
				return "!@#$%^&*()_+";
			}
		};
		CharacterRule splCharRule = new CharacterRule(specialChars);
		splCharRule.setNumberOfCharacters(2);
		// generate and return password
		String password = gen.generatePassword(10, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
		return password;
	}

	// encrypt a plain password string
	public String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(12);
		// convert from normal password to hashed value
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		// return encrypted password
		return (hashed_password);
	}

	// check if a hashed password value is equal to a plain password string
	public boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;
		// throw an exception if the hashed password is null or was made with a different pattern
		if (null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
		// verify that the passwords match
		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
		// return true if the actual password is the same
		return (password_verified);
	}

	// register a new user
	public User register(User u) {
		// throw an exception if attempting to create a user with a username that is already taken
		if (userDao.findByUsername(u.getUsername()) != null) {
			throw new UserAlreadyExistsException();
		}
		// throw an exception if attempting to create a user with an email that is already taken
		if (userDao.findByEmail(u.getEmail()) != null) {
			throw new EmailAlreadyExistsException();
		}
		// add password to database
		passDao.save(u.getPasswordHolder());
		// add user to database
		userDao.save(u);
		// user creation successful
		return u;
	}

	// get a user by their id
	public User getUserById(int user_id) {
		// find a user in the database with the provided id value
		Optional<User> oUser = userDao.findById(user_id);
		// throw an exception if no user was found
		if (oUser.get() == null) {
			throw new UsernameDoesNotExistException(); 
		}
		// return the user that was found
		return oUser.get();
	}

	// get a user by their username
	public User getUserByUsername(String username) {
		// find a user in the database with the provided username
		User user = userDao.findByUsername(username);
		// throw an exception if no user was found
		if (user == null) {
			throw new UsernameDoesNotExistException();
		}
		// return the user that was found
		return user;
	}

	// search for all users with a username that contain a specific string
	public List<User> searchByUsername(String str) {
		List<User> uList = new ArrayList<>();
		// get a list of all users that include a string in their username
		uList = userDao.findByUsernameContaining(str);
		// throw an exception if no user was found similar to the string
		if (uList.isEmpty()) {
			throw new SearchReturnedZeroResultsException();
		}
		// return a list of users that had a username like the search string
		return uList;
	}

	// update a user's attributes in the database
	public boolean updateUser(User user) {
		// find the user by username in the database
		User original = userDao.findByUsername(user.getUsername());
		// update their values
		userDao.save(user);
		// throw an exception if the database still holds their old values
		if (original.equals(user)) {
			throw new UpdateFailedException();
		}
		// user was successfully updated
		return true;
	}

	// add a relationship to the Follower Table
	public boolean addFollower(int user, int follower) {
		StoredFollowers sf = new StoredFollowers();
		// establish relationship between a user and their follower
		sf.setUserId(user);
		sf.setFollowerId(follower);
		// add relationship to Follower Table
		followerHDao.save(sf);
		// successfully added follower relationship
		return true;
	}

	// add a relationship to the Following Table
	public boolean addFollowing(int user, int following) {
		StoredFollowing sf = new StoredFollowing();
		// establish relationship between a user following another user
		sf.setUserId(user);
		sf.setFollowingId(following);
		// add relationship to Following Table
		followingHDao.save(sf);
		// successfully added following relationship
		return true;
	}

	// remove a relationship from the Follower Table
	public boolean removeFollower(int user_id, int follower_id) {
		// get a list of follower relationships for a user
		List<StoredFollowers> sfList = followerHDao.findAllByUserId(user_id);
		// loop through list to find matching follower id
		for (StoredFollowers sf : sfList) {
			if (sf.getFollowerId() == follower_id) {
				// remove follower relationship from database
				followerHDao.delete(sf);
			}
		}
		// successfully removed follower relationship
		return true;
	}

	// remove a relationship from the Following Table
	public boolean removeFollowing(int user_id, int following_id) {
		// get a list of following relationships for a user
		List<StoredFollowing> sfList = followingHDao.findAllByUserId(user_id);
		// loop through list to find matching following id
		for (StoredFollowing sf : sfList) {
			if (sf.getFollowingId() == following_id) {
				// remove following relationship from database
				followingHDao.delete(sf);
			}
		}
		// successfully removed following relationship
		return true;
	}

	// get a list of followers for a user
	public List<User> getUserFollowers(int user_id) {
		// get a list of follower relationships for a user
		List<StoredFollowers> sUsers = followerHDao.findAllByUserId(user_id);
		List<User> userList = new ArrayList<>();
		// loop through list of relationships to add each follower to a user list
		for (StoredFollowers i : sUsers) {
			userList.add(getUserById(i.getFollowerId()));
		}
		// return list of followers
		return userList;
	}

	// get a list of following for a user
	public List<User> getUserFollowing(int user_id) {
		// get a list of following relationships for a user
		List<StoredFollowing> sUsers = followingHDao.findAllByUserId(user_id);
		List<User> userList = new ArrayList<>();
		// loop through list of relationships to add each following to a user list
		for (StoredFollowing i : sUsers) {
			userList.add(getUserById(i.getFollowingId()));
		}
		// return list of following
		return userList;
	}

	// get a user to login
	public User login(String username, String password) {
		User user = new User();
		// find a user in the database by username
		user = userDao.findByUsername(username);
		// throw an exception if no user was found
		if (user == null) {
			throw new UsernameDoesNotExistException();
		}
		// throw an exception if the password was incorrect
		if (!checkPassword(password, user.getPasswordHolder().getHashedPassword())) {
			throw new InvalidCredentialsException();
		}
		// successfully log in as a user
		return user;
	}

	// allow a user to reset their password credential
	public void resetPassword(String email, String tempPassword, String newPassword) {
		// find a user in the database by email
		User user =  userDao.findByEmail(email);
		// throw an exception if the original password was incorrect
		if(!checkPassword(tempPassword, user.getPasswordHolder().getHashedPassword())) {
			throw new InvalidCredentialsException();
		}
		// encrypt new password
		String hashed = hashPassword(newPassword);
		// update current user's password credential
		StoredPassword sp = user.getPasswordHolder();
		sp.setHashedPassword(hashed);
		// update Password Table in database
		passDao.save(sp);
		// update User Table in database
		userDao.save(user);
	}

	// send a user an email to reset their password
	public void sendResetEmail(String email) {
		User user = new User();
		// find a user in the database by email
		user = userDao.findByEmail(email);
		// throw an exception if no user was found
		if (user == null) {
			throw new EmailDoesNotExistException();
		}
		// create new temporary password
		String temporaryPassword = generatePassayPassword();
		// create the email to send to user
		Email emailToSend = new Email(email);
		try {
			MimeMessage message = new MimeMessage(emailToSend.getSession());
			// format the email content
			message.setFrom(new InternetAddress(emailToSend.getSendFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(emailToSend.getSendTo()));
			message.setSubject("YAWP -- Password Reset");
			String messageBody = "<h6>You have requested a password reset.<h6> <br> Below is your username and new temporary password."
					+ "Once you've logged in, you will be asked to set your new password.<br>" + "<b>User</b>: "
					+ user.getUsername() + "<br>" + "<b>Pass</b>: " + temporaryPassword + "<br><br><br><br>"
					+ "If you have any issues or questions, please contact us:\n" + "<h4>YAWP Team<h4><br>"
					+ "<b>Phone</b>: 555-5555<br>" + "<b>Address</b>: 462 South 4th Street, Suite 1600<br>"
					+ "				   Louisville, KY 40202<br>";
			messageBody = "<font face=\"courier new\" size=\"10px\">" + messageBody + "</font></p>";
			message.setContent(messageBody, "text/html");
			// set the email delivery method
			Transport transport = emailToSend.getSession().getTransport("smtp");
			transport.connect(emailToSend.getHost(), emailToSend.getSendFrom(), emailToSend.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			// throw an exception if an error occurred sending the mail
			e.printStackTrace();
			throw new EmailNotSentException();
		}
		// if this is successful we hash the temp password, set it to the user and wait for them to login
		String hashedTemp = hashPassword(temporaryPassword);
		StoredPassword sp = user.getPasswordHolder();
		sp.setHashedPassword(hashedTemp);
		// update Password Table
		passDao.save(sp);
		// update User Table
		userDao.save(user);
	}
	
}
