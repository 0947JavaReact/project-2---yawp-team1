package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.User;



public interface UserDao extends JpaRepository<User, Integer>{

	public List<User> findAll();
	public User findByUsername(String username);
	public User findByUserId(int id);
	public void insertUser(User u);
	public List<User> findByUsernameContaining(String name);
	
	@Modifying
	@Query("update User u set u.bio=?1 where u.username=?2")
	public void setFixedBioFor(String bio, String username);
	@Modifying
	@Query("update User u set u.password=?1 where u.username=?2")
	public void setFixedPasswordFor(String password, String username);
	@Modifying
	@Query("update User u set u.email=?1 where u.username=?2")
	public void setFixedEmailFor(String email, String username);
	@Modifying
	@Query("update User u set u.pic_url=?1 where u.username=?2")
	public void setFixedPicUrlFor(String picUrl, String username);
	
	
}
