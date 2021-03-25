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

public class TransactionDAOImplTest {

	private static Logger log = Logger.getLogger(UserCreateTest.class);
	private static UserDAO userDao = new UserDAOImpl();
	private static AccountDAO accountDao = new AccountDAOImpl();
	private static TransactionDAO tranDao = new TransactionDAOImpl();
	private User testUser;
	private BankAccount testAccount;
	private Transaction testTran;

	@Before
	public void setUpUsers() {
		testUser = User.createUser("testUser1", "12341234", "testEmail1@test.com", UserGroup.customer);
		UserGroup.addToGroup(testUser);
		log.info(testUser);
		testAccount = accountDao.createAccount(testUser);
		testUser.addAccount(testAccount);

		testTran = new Transaction();
		testTran.setAccount(testAccount);
		testTran.setAmount(0.00);
		testTran.setStatus("pending");

	}

	@After
	public void tearDownUser() {

		log.info(testUser.getAccounts().get(0).toString());
		// first deletes the bank account - no on delete then cascade
		accountDao.deleteAccountById(testUser.getAccounts().get(0).getAccount_id());
		// then deletes the user
		userDao.deleteUser(testUser);

	}

	@Test
	public void test() {
		// make a transaction

		if (tranDao.executeWithdrawal(testTran) > 0) {
			log.info("It worked!!");
		}

	}
}
