package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.User;



public interface UserDao extends JpaRepository<User, Integer>{

	public List<User> findAll();
	public User findByUsername(String username);
	public List<User> findByUsernameContaining(String name);
	
/*
	@Modifying
	@Query("update users u set u.bio= :bio where u.user_name = :user_name")
	public void setFixedBioFor(@Param("bio") String bio,@Param("user_name") String user_name);
	
	@Modifying
	@Query("update users u set u.password=?1 where u.user_name=?2")
	public void setFixedPasswordFor(String password, String username);
	
	@Modifying
	@Query("update users u set u.email=?1 where u.user_name=?2")
	public void setFixedEmailFor(String email, String username);
	
	@Modifying
	@Query("update users u set u.pic_url=?1 where u.user_name=?2")
	public void setFixedPicUrlFor(String picUrl, String username);
	*/
	
}
