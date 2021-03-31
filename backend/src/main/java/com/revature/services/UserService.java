package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
import com.revature.exceptions.PasswordUpdateFailedException;
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

	// private UserRepository userRepo;

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	// Code from Passay allowing us to create a generated password.
	public String generatePassayPassword() {
		PasswordGenerator gen = new PasswordGenerator();
		CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
		CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
		lowerCaseRule.setNumberOfCharacters(2);

		CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
		CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
		upperCaseRule.setNumberOfCharacters(2);

		CharacterData digitChars = EnglishCharacterData.Digit;
		CharacterRule digitRule = new CharacterRule(digitChars);
		digitRule.setNumberOfCharacters(2);

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

		String password = gen.generatePassword(10, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
		return password;
	}

	public String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return (hashed_password);
	}

	public boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if (null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return (password_verified);
	}

	public User register(User u) {
		if (userDao.findByUsername(u.getUsername()) != null) {
			throw new UserAlreadyExistsException();
		}
		if (userDao.findByEmail(u.getEmail()) != null) {
			throw new EmailAlreadyExistsException();
		}

		passDao.save(u.getPasswordHolder());
		userDao.save(u);
		return u;
	}

	public User getUserById(int id) {
		
		Optional<User> oUser = userDao.findById(id);

		if (oUser.get() == null) {
			throw new UsernameDoesNotExistException(); 
		}
		
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
		User original = userDao.findByUsername(user.getUsername());
		userDao.save(user);
		if (original.equals(user)) {
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
		System.out.println("in remove follower list: " + sfList);
		for (StoredFollowers sf : sfList) {
			if (sf.getFollowerId() == follower) {
				System.out.println(sf);
				followerHDao.delete(sf);
			}
		}
		return true;
	}

	public boolean removeFollowing(int user, int following) {
		System.out.println("user id: " + user + " /nfollowing: " + following);
		List<StoredFollowing> sfList = followingHDao.findAllByUserId(user);
		System.out.println("in remove following list: " + sfList);
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

		if (user == null) {
			throw new UsernameDoesNotExistException();
		}

		if (!checkPassword(password, user.getPasswordHolder().getHashedPassword())) {
			throw new InvalidCredentialsException();
		}
		return user;
	}

	public void resetPassword(String email, String tempPassword, String newPassword) {
		User user =  userDao.findByEmail(email);
		System.out.println("Email: " + email);
		System.out.println("temp: " + tempPassword);
		System.out.println("new: " + newPassword);
		if(!checkPassword(tempPassword, user.getPasswordHolder().getHashedPassword())) {
			System.out.println("throwing e");
			throw new InvalidCredentialsException();
		}
		String hashed = hashPassword(newPassword);
		StoredPassword sp = user.getPasswordHolder();
		
		sp.setHashedPassword(hashed);
		passDao.save(sp);
		userDao.save(user);
		
	}

	public void sendResetEmail(String email) {
		User user = new User();
		user = userDao.findByEmail(email);

		System.out.println("At top of send reset: " + email);

		if (user == null) {
			System.out.println("email doesn't exist");
			throw new EmailDoesNotExistException();
		}
		// create new temporary password.
		String temporaryPassword = generatePassayPassword();
		System.out.println("temp password: " + temporaryPassword);

		// Create the email
		Email emailToSend = new Email(email);
		System.out.println(emailToSend);
		try {
			MimeMessage message = new MimeMessage(emailToSend.getSession());

			message.setFrom(new InternetAddress(emailToSend.getSendFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(emailToSend.getSendTo()));
			message.setSubject("YAWP -- Password Reset");
			String messageBody = "<h6>You've requested a password reset.<h6> <br> Below is your username and new temporary password."
					+ "Once you've logged in, you will be asked to set you're new password.<br>" + "<b>User</b>: "
					+ user.getUsername() + "<br>" + "<b>Pass</b>: " + temporaryPassword + "<br><br><br><br>"
					+ "If you have any issues or questions please conctact us:\n" + "<h4>YAWP Team<h4><br>"
					+ "<b>Phone</b>: 555-5555<br>" + "<b>Address</b>: 462 South 4th Street, Suite 1600<br>"
					+ "				   Louisville, KY 40202<br>";
			messageBody = "<font face=\"courier new\" size=\"10px\">" + messageBody + "</font></p>";
			message.setContent(messageBody, "text/html");

			Transport transport = emailToSend.getSession().getTransport("smtp");
			transport.connect(emailToSend.getHost(), emailToSend.getSendFrom(), emailToSend.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailNotSentException();
		}

		// if this is successful we hash the temp password set it to the user and wait
		// for them to login
		String hashedTemp = hashPassword(temporaryPassword);
		StoredPassword sp = user.getPasswordHolder();
		sp.setHashedPassword(hashedTemp);
		passDao.save(sp);
		userDao.save(user);
	}
}
