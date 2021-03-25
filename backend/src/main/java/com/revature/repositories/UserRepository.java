package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.User;

@Transactional
@Repository("userDao")
public class UserRepository {

	private SessionFactory sesFactory;

	public UserRepository() {
		super();
	}

	@Autowired
	public UserRepository(SessionFactory sesFactory) {
		super();
		this.sesFactory = sesFactory;

	}

	public void insert(User u) {
		sesFactory.getCurrentSession().save(u);
	}
	
	public User selectById(int id) {
		return sesFactory.getCurrentSession().get(User.class, id);
	}
	
	public User selectByName(String name) {
		
		List<User> fList = new ArrayList<>();
		fList = sesFactory.getCurrentSession().createQuery("from User where name = '"+name+"'",User.class).list();
		if (fList.size() == 0) {
			System.out.println("Panic");
			return null;
		}
		return fList.get(0);

		
	}
	
	public List<User> selectAll() {
		return sesFactory.getCurrentSession().createQuery("from User", User.class).list();
	}
	
	public void update(User u) {
		sesFactory.getCurrentSession().update(u);
	}
}
