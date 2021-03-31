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

	// the test file will use this implementation of YawpService
	@Autowired
	private YawpDao ydao;
	@Autowired
	private FollowerHolderDao followerHDao;
	@Autowired
	private FollowingHolderDao followingHDao;
	@Autowired
	private UserDao udao;
	
	@Override
	// get a list of all yawps from the database
	public List<Yawp> getAllYawps() {
		return ydao.findAll();
	}
	
	@Override
	// get a yawp from the database by its id
	public Yawp getYawp(int yawp_id) {
		return ydao.findById(yawp_id).get();
	}

	@Override
	// add a new yawp to the database
	public Yawp register(Yawp yawp){
		return ydao.save(yawp);
	}
	
	@Override
	// add a like to a yawp
	public void like(int yawpId, int userId) {
		// find the yawp by its id
		Yawp yawp = ydao.findById(yawpId).get();
		// find the user by their id, add user to set of users that liked the yawp
		yawp.addLike(udao.findById(userId).get());
		// update the Yawp Table
		ydao.save(yawp);	
	}
	
	@Override
	// remove a like from a yawp
	public void unlike(int yawpId, int userId) {
		// find the yawp by its id
		Yawp yawp = ydao.findById(yawpId).get();
		// find the user by their id, remove user from set of users that liked the yawp
		yawp.removeLike(udao.findById(userId).get());
		// update the Yawp Table
		ydao.save(yawp);	
	}

	@Override
	// get a list of yawps from a user
	public List<Yawp> getYawpsByUser(int user_id) {
		List<Yawp> yList = new ArrayList<>();
		// get a list of yawps with the same author id
		yList = ydao.findByAuthorId(user_id);
		// sort the yawp list in reverse order
		Collections.sort(yList, Comparator.reverseOrder());
		// successfully found yawps from a user
		return yList;
	}

	@Override
	// get a list of yawps from followers
	public List<Yawp> getYawpsByFollowers(int user_id) {
		List<Yawp> yList = new ArrayList<>();
		// get list of follower relationships
		List<StoredFollowers> followerList = followerHDao.findAllByUserId(user_id);
		// loop through the follower list, add all yawps of each follower to yawp list
		for (StoredFollowers i : followerList) {
			yList.addAll(getYawpsByUser(i.getFollowerId()));
		}
		// sort the yawp list in reverse order
		Collections.sort(yList, Comparator.reverseOrder());
		// successfully found follower yawps
		return yList;
	}
	
	@Override
	// get a list of yawps from following
	public List<Yawp> getYawpsByFollowing(int user_id) {
		List<Yawp> yList = new ArrayList<>();
		// get list of following relationships
		List<StoredFollowing> followingList = followingHDao.findAllByUserId(user_id);
		// loop through the following list, add all yawps of each following to yawp list
		for (StoredFollowing i:followingList) {
			yList.addAll(getYawpsByUser(i.getFollowingId()));
		}
		// sort the yawp list in reverse order
		Collections.sort(yList,Comparator.reverseOrder());
		// successfully found following yawps
		return yList;
	}
	
}
