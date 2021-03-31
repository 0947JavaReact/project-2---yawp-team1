package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.StoredFollowing;

public interface FollowingHolderDao extends JpaRepository<StoredFollowing, Integer>{

	// methods Spring handles for the FollowingHolder
	public List<StoredFollowing> findAllByUserId(int id);
	public List<StoredFollowing> findAllByFollowingId(int id);
	
}
