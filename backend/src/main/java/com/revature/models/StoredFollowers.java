package com.revature.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@Id
	@Column(name = "follower_PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int primaryKey;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "follower_id")
	private int followerId;

	public StoredFollowers(int userId, int followerId) {
		super();
		this.userId = userId;
		this.followerId = followerId;
	}

//	@JsonBackReference
//	@OneToMany(mappedBy = "followersHolder", fetch = FetchType.LAZY)
//	private Set<User> followers;
	
	
}
