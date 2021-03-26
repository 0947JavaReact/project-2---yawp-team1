package com.revature.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
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

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "followerTable")
	private Set<User> followers;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="followingTable")
	private Set<User> following;

	//private Follower followingHolder;

	

	public User(String username, String password, String email, String bio, String picUrl, Set<User> followers, Set<User> following) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
		this.followers = followers;
	}

	
	public User(String username, String password, String email, String bio) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
	}


	public User(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
