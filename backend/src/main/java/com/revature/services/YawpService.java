package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public void register(String message, User user) throws SQLException{
		Yawp yawp =  new Yawp(message, user.getUserId());
		ydao.save(yawp);
	}
	
	public List<Yawp> getYawpsByUser(User u) {
		
		return this.ydao.findByAuthorId(u.getUserId());
	}
	
}
