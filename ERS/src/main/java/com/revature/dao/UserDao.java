package com.revature.dao;

import com.revature.models.User;

public interface UserDao extends GenericDao<User> {
	public User getByEmail(String email);

	public User getByUsername(String username);
}
