package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.StoredFollowing;

public interface FollowingHolderDao extends JpaRepository<StoredFollowing, Integer>{

	public List<StoredFollowing> findAllByUserId(int id);
	public List<StoredFollowing> findAllByFollowingId(int id);
}
