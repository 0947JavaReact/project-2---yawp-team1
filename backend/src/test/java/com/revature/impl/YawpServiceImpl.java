package com.revature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.models.StoredFollowers;
import com.revature.models.StoredFollowing;
import com.revature.models.Yawp;
import com.revature.repositories.FollowerHolderDao;
import com.revature.repositories.FollowingHolderDao;
import com.revature.repositories.UserDao;
import com.revature.repositories.YawpDao;
import com.revature.services.YawpService;

public class YawpServiceImpl extends YawpService{

	@Autowired
	private YawpDao ydao;
	
	@Autowired
	private FollowerHolderDao followerHDao;
	
	@Autowired
	private FollowingHolderDao followingHDao;
	
	@Autowired
	private UserDao udao;
	
	
	@Override
	public List<Yawp> getAllYawps() {
		List<Yawp> list = ydao.findAll();
		Collections.sort(list, Comparator.reverseOrder());
		return ydao.findAll();
	}
	
	@Override
	public Yawp getYawp(int id) {
		return ydao.findById(id).get();
	}

	@Override
	public Yawp register(Yawp yawp){
		return ydao.save(yawp);
	}
	
	@Override
	public void like(int yawpId, int userId) {
		Yawp yawp = ydao.findById(yawpId).get();
		yawp.addLike(udao.findById(userId).get());
		ydao.save(yawp);	
	}
	
	@Override
	public void unlike(int yawpId, int userId) {
		Yawp yawp = ydao.findById(yawpId).get();
		yawp.removeLike(udao.findById(userId).get());
		ydao.save(yawp);	
	}

	@Override
	public List<Yawp> getYawpsByUser(int id) {
		List<Yawp> yList = new ArrayList<>();
		yList = ydao.findByAuthorId(id);
		System.out.println(yList);
		Collections.sort(yList, Comparator.reverseOrder());;
		return yList;
	}

	@Override
	public List<Yawp> getYawpsByFollowers(int id) {
		List<Yawp> yList = new ArrayList<>();
		List<StoredFollowers> followerList = followerHDao.findAllByUserId(id);
		

		for (StoredFollowers i : followerList) {
			yList.addAll(getYawpsByUser(i.getFollowerId()));
			System.out.println("UserId: " + i.getFollowerId());
			System.out.println(getYawpsByUser(i.getFollowerId()));
		}
		Collections.sort(yList, Comparator.reverseOrder());

		return yList;
	}
	
	@Override
	public List<Yawp> getYawpsByFollowing(int id) {
		List<Yawp> yList = new ArrayList<>();
		List<StoredFollowing> followingList = followingHDao.findAllByUserId(id);
		System.out.println(followingList);

		
		for (StoredFollowing i:followingList) {
			yList.addAll(getYawpsByUser(i.getFollowingId()));
			System.out.println("UserId: " + i.getFollowingId());
			System.out.println(getYawpsByUser(i.getFollowingId()));
		}
		Collections.sort(yList,Comparator.reverseOrder());

		return yList;
	}
}
