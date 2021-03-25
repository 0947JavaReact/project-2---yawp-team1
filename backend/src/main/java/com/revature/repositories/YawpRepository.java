package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Yawp;


@Transactional
@Repository("yawpDao")
public class YawpRepository {
	private SessionFactory sesFactory;

	public YawpRepository() {
		super();
	}

	@Autowired
	public YawpRepository(SessionFactory sesFactory) {
		super();
		this.sesFactory = sesFactory;

	}

	public void insert(Yawp u) {
		sesFactory.getCurrentSession().save(u);
	}
	
	public Yawp selectById(int id) {
		return sesFactory.getCurrentSession().get(Yawp.class, id);
	}
	
	public List<Yawp> selectByName(int id) {
		
		List<Yawp> fList = new ArrayList<>();
		fList = sesFactory.getCurrentSession().createQuery("from Yawp where author_id = '"+id+"'",Yawp.class).list();
		if (fList.size() == 0) {
			System.out.println("Panic");
			return null;
		}
		return fList;

		
	}
	
	public List<Yawp> selectAll() {
		return sesFactory.getCurrentSession().createQuery("from Yawp", Yawp.class).list();
	}

}
