package com.revature.models;

import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "FollowingHolder")
public class StoredFollowing {

	@Id
	@Column(name = "following_PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private int primaryKey;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "following_id")
	private int followingId;

	public StoredFollowing(int userId, int followingId) {
		super();
		this.userId = userId;
		this.followingId = followingId;
	}
	
//	@JsonBackReference
//	@OneToMany(mappedBy = "followingHolder", fetch = FetchType.LAZY)
//	private Set<User> following;

	
}
