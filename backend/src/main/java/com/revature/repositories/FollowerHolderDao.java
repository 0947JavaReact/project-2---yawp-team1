package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.models.StoredFollowers;

public interface FollowerHolderDao extends JpaRepository<StoredFollowers, Integer>{

	// methods Spring handles for the FollowerHolder
	public List<StoredFollowers> findAllByUserId(int id);
	public List<StoredFollowers> findAllByFollowerId(int id);

}
