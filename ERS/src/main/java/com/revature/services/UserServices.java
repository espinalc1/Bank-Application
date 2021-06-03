package com.revature.services;

import com.revature.models.User;

public interface UserServices extends GenericServices<User> {
	public User loginByEmail(String email, String password);

	public User loginByUsername(String username, String password);

	public int updateInformation(User user);
}
