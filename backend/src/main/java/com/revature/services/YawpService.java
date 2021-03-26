package com.revature.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UnlikeException;
import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.repositories.UserDao;
import com.revature.repositories.YawpDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("yawpServ")
public class YawpService {
	private YawpDao ydao;
	private UserDao udao;

	public List<Yawp> getAllYawps() {
		return ydao.findAll();
	}

	public Yawp register(Yawp yawp){
		return ydao.save(yawp);
	}
	
	public void like(int id) {
		Yawp yawp = ydao.findById(id).get();
		yawp.addLike(udao.findById(id).get());
		ydao.save(yawp);	
	}
	
	public void unlike(int id) {
		Yawp yawp = ydao.findById(id).get();
		
		yawp.removeLike(udao.findById(id).get());
		ydao.save(yawp);	
	}

	public List<Yawp> getYawpsByUser(int id) {
		List<Yawp> yList = new ArrayList<>();
		yList = ydao.findByAuthorId(id);
		Collections.sort(yList);
		return yList;
	}

	public List<Yawp> getYawpsByFollowers(int id) {
		List<Yawp> yList = new ArrayList<>();
		Set<User> followerList = udao.findById(id).get().getFollowers();

		for (User u : followerList) {
			yList.addAll(getYawpsByUser(u.getUserId()));
		}
		Collections.sort(yList);

		return yList;
	}
	
	public List<Yawp> getYawpsByFollowing(int id) {
		List<Yawp> yList = new ArrayList<>();
		Set<User> followingList = udao.findById(id).get().getFollowing();

		
		for (User u:followingList) {
			yList.addAll(getYawpsByUser(u.getUserId()));
		}
		Collections.sort(yList);

		return yList;
	}

}
