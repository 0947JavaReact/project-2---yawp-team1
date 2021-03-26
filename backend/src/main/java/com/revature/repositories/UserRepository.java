package com.revature.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class UserRepository {

	private SessionFactory sesFact;

	

	public List<Integer> getFollowers(int id) {

		List<Integer> uList = sesFact.getCurrentSession().createNativeQuery(
				"SELECT u2.user_id FROM users u "
				+ "INNER JOIN followers_table ft ON u.:user_id = ft.user_user_id "
				+ "INNER JOIN users u2 ON ft.followers_user_id = u2.user_id "
				+ "WHERE u.user_id ='"
						+ id + "'",
				Integer.class).list();
		return uList;
	}

	public List<Integer> getFollowing(int id) {
		List<Integer> uList = sesFact.getCurrentSession().createNativeQuery("SELECT u2.user_id FROM users u \r\n"
				+ "INNER JOIN following_table ft ON u.user_id = ft.user_user_id \r\n" + "INNER JOIN users u2  \r\n"
				+ "ON ft.following_user_id = u2.user_id \r\n" + "WHERE u.user_id ='" + id + "'", Integer.class).list();
		return uList;

	}

	

}
