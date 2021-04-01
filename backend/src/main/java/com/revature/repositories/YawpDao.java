package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Yawp;

public interface YawpDao extends JpaRepository<Yawp, Integer>{

	// methods Spring handles for the Yawp
	public List<Yawp> findAll();
	public List<Yawp> findByAuthorId(int id);
	
}
