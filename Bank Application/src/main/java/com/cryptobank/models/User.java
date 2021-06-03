package com.cryptobank.models;

import java.util.ArrayList;
import java.util.List;

import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.UserDAOImpl;

public class User {
	private static UserDAO dao = new UserDAOImpl();
	private Integer userId;
	private String userName;
	private String password;
	private String email;
	private UserGroup group;
	private List<BankAccount> accounts;

	public User() {
		this.accounts = new ArrayList<>();
	}

	public User(Integer userId, String userName, String password, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	private User(String userName, String password, String email, UserGroup ug) {
		super();
		this.accounts = new ArrayList<>();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.group = ug;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}

	// use this for email as well
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;

	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;

	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankAccount> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(BankAccount account) {
		this.accounts.add(account);
	}

	public static User getUser(String userName, String password) {
		// what happens if this user doesn't exist
		
		return dao.getUserByUserName(userName);
	}

	public static List<User> getCustomers() {

		return dao.getAllCustomers();
	}

	public static User createUser(String userName, String password, String email, UserGroup ug) {
		User user = new User(userName, password, email, ug);
		user.setUserId(dao.createUser(user));
		return user;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + "]";
	}


}
