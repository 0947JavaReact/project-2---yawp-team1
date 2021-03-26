package com.revature.repositories;

import java.util.List;

import com.revature.models.Yawp;

public interface YawpDao {

	public List<Yawp> findAll();
	public Yawp findByYawpId(int id);
	public List<Yawp> findByAuthorId(int id);
	public void insertYawp(Yawp yawp);
	
}
