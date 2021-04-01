package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "FollowerHolder")
public class StoredFollowers {

	// Columns in the Followers Table
	@Id
	@Column(name = "follower_PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int primaryKey;
	
	@Column(name = "user_id")
	private int userId;
	
	// This is the person who is following the user
	@Column(name = "follower_id")
	private int followerId;

	// StoredFollowers constructor
	public StoredFollowers(int userId, int followerId) {
		super();
		this.userId = userId;
		this.followerId = followerId;
	}
	
}
