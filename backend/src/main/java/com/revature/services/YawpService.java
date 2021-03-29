package com.revature.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.StoredFollowers;
import com.revature.models.StoredFollowing;
import com.revature.models.Yawp;
import com.revature.repositories.FollowerHolderDao;
import com.revature.repositories.FollowingHolderDao;
import com.revature.repositories.UserDao;
import com.revature.repositories.YawpDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("yawpServ")
public class YawpService {
	private YawpDao ydao;
	private FollowerHolderDao followerHDao;
	private FollowingHolderDao followingHDao;
	private UserDao udao;

	public List<Yawp> getAllYawps() {
		return ydao.findAll();
	}
	
	public Yawp getYawp(int id) {
		return ydao.findById(id).get();
	}

	public Yawp register(Yawp yawp){
		return ydao.save(yawp);
	}
	
	public void like(int yawpId, int userId) {
		Yawp yawp = ydao.findById(yawpId).get();
		yawp.addLike(udao.findById(userId).get());
		ydao.save(yawp);	
	}
	
	public void unlike(int yawpId, int userId) {
		Yawp yawp = ydao.findById(yawpId).get();
		yawp.removeLike(udao.findById(userId).get());
		ydao.save(yawp);	
	}

	public List<Yawp> getYawpsByUser(int id) {
		List<Yawp> yList = new ArrayList<>();
		yList = ydao.findByAuthorId(id);
		Collections.sort(yList, Comparator.reverseOrder());;
		return yList;
	}

	public List<Yawp> getYawpsByFollowers(int id) {
		List<Yawp> yList = new ArrayList<>();
		List<StoredFollowers> followerList = followerHDao.findAllByUserId(id);

		for (StoredFollowers i : followerList) {
			yList.addAll(getYawpsByUser(i.getFollowerId()));
		}
		Collections.sort(yList, Comparator.reverseOrder());

		return yList;
	}
	
	public List<Yawp> getYawpsByFollowing(int id) {
		List<Yawp> yList = new ArrayList<>();
		List<StoredFollowing> followingList = followingHDao.findAllByUserId(id);

		
		for (StoredFollowing i:followingList) {
			yList.addAll(getYawpsByUser(i.getFollowingId()));
		}
		Collections.sort(yList,Comparator.reverseOrder());

		return yList;
	}

}
