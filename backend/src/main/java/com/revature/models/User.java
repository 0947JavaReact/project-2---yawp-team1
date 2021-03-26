package com.revature.models;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "password_FK", nullable = false)
	private StoredPassword passwordHolder;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "bio")
	private String bio;

	@Column(name = "pic_url")
	private String picUrl;

//	@JsonManagedReference
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "follower_FK,")
//	private StoredFF ffHolder;
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="followersTable")
	@Setter(AccessLevel.NONE)
	private Set<User> followers;
	
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="followingTable")
	@Setter(AccessLevel.NONE)
	private Set<User> following;

	//private Follower followingHolder;	
	public User(String username, StoredPassword password, String email, String bio) {
		super();
		this.username = username;
		this.passwordHolder = password;
		this.email = email;
		this.bio = bio;
	}


	public User(String username, StoredPassword password, String email) {
		super();
		this.username = username;
		this.passwordHolder = password;
		this.email = email;
	}


	public User(String username, StoredPassword passwordHolder, String email, String bio, String picUrl,
			Set<User> followers, Set<User> following) {
		super();
		this.username = username;
		this.passwordHolder = passwordHolder;
		this.email = email;
		this.bio = bio;
		this.picUrl = picUrl;
		this.followers = followers;
		this.following = following;
	}


	
	@PostConstruct
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}


	@PostConstruct
	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	
	
}
