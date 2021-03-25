package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Yawp;
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
}
