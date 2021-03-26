package com.revature.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UnlikeException;
import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.repositories.YawpDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service("yawpServ")
public class YawpService {
	private YawpDao ydao;

	public List<Yawp> getAllYawps() {
		return ydao.findAll();
	}

	public Yawp register(Yawp yawp){
		return ydao.save(yawp);
	}
	
	public void like(int id) {
		Yawp yawp = ydao.findById(id).get();
		yawp.addLike();
		ydao.save(yawp);	
	}
	
	public void unlike(int id) {
		Yawp yawp = ydao.findById(id).get();
		
		if(yawp.getLikesCount() > 0) {
			yawp.removeLike();
			throw new UnlikeException();
		}
		ydao.save(yawp);	
	}

	public List<Yawp> getYawpsByUser(User u) {
		List<Yawp> yList = new ArrayList<>();
		yList = ydao.findByAuthorId(u.getUserId());
		Collections.sort(yList);
		return yList;
	}

	public List<Yawp> getYawpsByFollowers(List<User> followerList) {
		List<Yawp> yList = new ArrayList<>();

		for (User u : followerList) {
			yList.addAll(getYawpsByUser(u));
		}
		Collections.sort(yList);

		return yList;
	}
	
	public List<Yawp> getYawpsByFollowing(List<User> followingList) {
		List<Yawp> yList = new ArrayList<>();
		
		for (User u:followingList) {
			yList.addAll(getYawpsByUser(u));
		}
		Collections.sort(yList);

		return yList;
	}

}
