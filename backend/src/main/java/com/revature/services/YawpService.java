package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.models.Yawp;
import com.revature.repositories.UserRepository;
import com.revature.repositories.YawpRepository;

@Service("yawpServ")
public class YawpService {
	private YawpRepository yrepo;

	public YawpService() {
		super();
	}

	@Autowired
	public YawpService(YawpRepository urepo) {
		super();
		this.yrepo = urepo;
	}

	public List<Yawp> getAllYawps() {
		return yrepo.selectAll();
	}
	
	public void register(String message, User user) {
		Yawp yawp =  new Yawp(message, user.getUserId());
		this.yrepo.insert(yawp);
	}
	
	public List<Yawp> getYawpsByUser(User u) {
		
		return this.yrepo.selectByName(u.getUserId());
	}
	
}
