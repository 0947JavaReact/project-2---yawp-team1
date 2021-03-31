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
@Table(name = "FollowingHolder")
public class StoredFollowing {

	// Columns in the Following Table
	@Id
	@Column(name = "following_PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int primaryKey;

	@Column(name = "user_id")
	private int userId;
	
	// User is following this person
	@Column(name = "following_id")
	private int followingId;

	// StoredFollowing constructor
	public StoredFollowing(int userId, int followingId) {
		super();
		this.userId = userId;
		this.followingId = followingId;
	}
	
}
