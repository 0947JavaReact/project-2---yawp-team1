package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.StoredFollowers;

public interface FollowerHolderDao extends JpaRepository<StoredFollowers, Integer>{

	public List<StoredFollowers> findAllByUserId(int id);
	public List<StoredFollowers> findAllByFollowerId(int id);

//	@Query("SELECT u2.userId From User u INNER JOIN StoredFollowers f ON u.userId =  f.user_user_id INNER JOIN User u2 on f.followers_user_id = u2.userId WHERE u.userId = ?1")
//	public List<Integer> findAllFollowers(int id);
}
