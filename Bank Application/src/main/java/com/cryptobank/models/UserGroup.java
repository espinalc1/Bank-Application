package com.cryptobank.models;

import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.UserDAOImpl;

public class UserGroup {

	public static UserGroup employee = new UserGroup(1, "employee");
	public static UserGroup customer = new UserGroup(2, "customer");
	public static UserDAO dao = new UserDAOImpl();
	private Integer userGroupId;
	private String userGroupName;

	private UserGroup(Integer userGroupId, String userGroupName) {
		super();
		this.userGroupId = userGroupId;
		this.userGroupName = userGroupName;
	}

	public static boolean addToGroup(User user) {
		// get method from the DAO
		// this will return an int to show that it was successful
		
		int k = dao.assignUserToGroup(user);
		if (k > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static UserGroup getUserGroup(User user) {
		return dao.getUserGroup(user);
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	protected void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Override
	public String toString() {
		return "UserGroup [userGroupName=" + userGroupName + "]";
	}
}
