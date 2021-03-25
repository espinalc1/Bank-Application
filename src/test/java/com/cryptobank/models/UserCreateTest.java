package com.cryptobank.models;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.TransactionDAO;
import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.AccountDAOImpl;
import com.cryptobank.DAOImpl.TransactionDAOImpl;
import com.cryptobank.DAOImpl.UserDAOImpl;

public class UserCreateTest {
	private static Logger log = Logger.getLogger(UserCreateTest.class);
	private UserDAO userDao = new UserDAOImpl();
	private AccountDAO accountDao = new AccountDAOImpl();
	private TransactionDAO tranDao = new TransactionDAOImpl();
	private User testUser;
	private User testUser2;
	private BankAccount testAccount; 
	private BankAccount testAccount2;
	private Transaction testTran;

	@Before
	public void setUpUsers() {
		testUser = new User();
		testUser.setUserName("testUser1");
		testUser.setEmail("testEmail1@test.com");
		testUser.setPassword("12341234");
		testUser.setGroup(UserGroup.customer);
		testUser.setUserId(userDao.createUser(testUser));
		UserGroup.addToGroup(testUser);

		testAccount = accountDao.createAccount(testUser);
		testUser.addAccount(testAccount);

		testUser2 = new User();
		testUser2.setUserName("testUser2");
		testUser2.setEmail("testEmail2@test.com");
		testUser2.setPassword("12341234");
		testUser2.setGroup(UserGroup.customer);
		testUser2.setUserId(userDao.createUser(testUser2));
		UserGroup.addToGroup(testUser2);

		testAccount2 = accountDao.createAccount(testUser2);
		testUser2.addAccount(testAccount2);

	}

	@Before
	public void setUpTransaction() {
		testTran = new Transaction();
		testTran.setAccount(testAccount);
		testTran.setAmount(40.00);
		testTran.setStatus("pending");

	}

	@After
	public void tearDownUser() {

		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser);

		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser2.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser2);
	}

	@Test
	public void test1() {

		log.info(tranDao.executeWithdrawal(testTran));

	}
}
