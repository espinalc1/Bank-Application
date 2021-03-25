package com.cryptobank.DAO;

import java.util.List;

import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;

public interface UserDAO {
	public int createUser(User user);
	// get user
	// update user details

	public boolean checkAvailable(String field, String value);

	public User getUserByEmail(String user_email);

	public User getUserByUserName(String user_name);

	public int assignUserToGroup(User user);

	public UserGroup getUserGroup(User user);

	public List<User> getAllCustomers();
	
	public int deleteUser(User user);
}
