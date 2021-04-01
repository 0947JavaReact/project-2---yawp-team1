package com.revature.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

public class UserRepository {

	private SessionFactory sesFact;

	// custom native query to get a list of followers
	public List<Integer> getFollowers(int user_id) {

		List<Integer> uList = sesFact.getCurrentSession().createNativeQuery(
				"SELECT u2.user_id FROM users u "
				+ "INNER JOIN followers_table ft ON u.user_id = ft.user_user_id "
				+ "INNER JOIN users u2 ON ft.followers_user_id = u2.user_id "
				+ "WHERE u.user_id = '" + user_id + "'", Integer.class).list();
		return uList;
	}

	// custom native query to get a list of following
	public List<Integer> getFollowing(int user_id) {
		List<Integer> uList = sesFact.getCurrentSession().createNativeQuery(
				"SELECT u2.user_id FROM users u "
				+ "INNER JOIN following_table ft ON u.user_id = ft.user_user_id "
				+ "INNER JOIN users u2 ON ft.following_user_id = u2.user_id "
				+ "WHERE u.user_id = '" + user_id + "'", Integer.class).list();
		return uList;
	}

}
