package com.revature.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

	// Columns in the Users Table
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int userId;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "password_FK", nullable = false)
	private StoredPassword passwordHolder;

	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "bio")
	private String bio;

	@Column(name = "pic_url")
	private String picUrl;

	// User constructors
	public User(String username, StoredPassword password, String email, String bio) {
		super();
		this.username = username;
		this.passwordHolder = password;
		this.email = email;
		this.bio = bio;
		this.picUrl = "https://robertsrevbucket.s3-us-west-1.amazonaws.com/default-profile-picture.jpeg";
	}
	public User(String username, StoredPassword password, String email) {
		super();
		this.username = username;
		this.passwordHolder = password;
		this.email = email;
		this.picUrl = "https://robertsrevbucket.s3-us-west-1.amazonaws.com/default-profile-picture.jpeg";
	}
	public User(String username, StoredPassword passwordHolder, String email, String bio, String picUrl) {
		super();
		this.username = username;
		this.passwordHolder = passwordHolder;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
	}

}
