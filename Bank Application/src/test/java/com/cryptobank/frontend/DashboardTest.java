package com.cryptobank.frontend;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.AccountDAOImpl;
import com.cryptobank.DAOImpl.UserDAOImpl;
import com.cryptobank.models.BankAccount;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;

public class DashboardTest {
	private UserDAO userDao = new UserDAOImpl();
	private AccountDAO accountDao = new AccountDAOImpl();
	private User testUser;
	private BankAccount testAccount;
	private Scanner sc;

	@Before
	public void setUp() {
		sc = new Scanner(System.in);
		testUser = new User();
		testUser.setUserName("testUserN2");
		testUser.setEmail("testEmail2@testCo.com");
		testUser.setPassword("12341234");
		testUser.setGroup(UserGroup.customer);
		testUser.setUserId(userDao.createUser(testUser));
		UserGroup.addToGroup(testUser);

		testAccount = accountDao.createAccount(testUser);
		testUser.addAccount(testAccount);
	}

	@After
	public void tearDown() {

		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser);
	}

	@Test
	public void test() {
		Dashboard.getDashboard().CustomerDataBase(sc, testUser);
	}

}
