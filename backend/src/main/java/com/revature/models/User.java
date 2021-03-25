package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "bio", nullable = false)
	private String bio;

	@Column(name = "pic_url")
	private String picUrl;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "followers")
	private List<User> followersIds;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name = "following")
	private List<User> followingIds;

	public User() {
		super();
	}

	public User(String username, String password, String email, String bio, String picUrl, List<User> followersIds,
			List<User> followingIds) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
		this.followersIds = followersIds;
		this.followingIds = followingIds;
	}

	public User(int userId, String username, String password, String email, String bio, String picUrl,
			List<User> followersIds, List<User> followingIds) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
		this.followersIds = followersIds;
		this.followingIds = followingIds;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public List<User> getFollowersIds() {
		return followersIds;
	}

	public void setFollowersIds(List<User> followersIds) {
		this.followersIds = followersIds;
	}

	public List<User> getFollowingIds() {
		return followingIds;
	}

	public void setFollowingIds(List<User> followingIds) {
		this.followingIds = followingIds;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", bio=" + bio + ", picUrl=" + picUrl + ", followersIds=" + followersIds + ", followingIds="
				+ followingIds + "]";
	}
	
}
