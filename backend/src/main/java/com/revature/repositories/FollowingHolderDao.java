package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.StoredFollowing;

public interface FollowingHolderDao extends JpaRepository<StoredFollowing, Integer>{

	public List<StoredFollowing> findAllByUserId(int id);
	public List<StoredFollowing> findAllByFollowingId(int id);
//	@Query("SELECT u2.userId From User u INNER JOIN StoredFollowing f ON u.userId =  f.user_user_id INNER JOIN User u2 on f.followers_user_id = u2.userId WHERE u.userId = ?1")
//	public List<Integer> findAllFollowing(int id);
}
