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

public class TransferTest {
	private static Logger log = Logger.getLogger(UserCreateTest.class);
	private static UserDAO userDao = new UserDAOImpl();
	private static AccountDAO accountDao = new AccountDAOImpl();
	private static TransactionDAO tranDao = new TransactionDAOImpl();
	private User testUser;
	private User testUser2;
	private BankAccount testAccount;
	private BankAccount testAccount2;
	private Transfer testTran;

	@Before
	public void setUpUsers() {
		testUser = User.createUser("testUser9", "12341234", "testEmail9@test.com", UserGroup.customer);
		UserGroup.addToGroup(testUser);
		log.info(testUser);
		testAccount = accountDao.createAccount(testUser);
		testUser.addAccount(testAccount);
		log.info(testAccount.toString());

		testUser2 = User.createUser("testUser10", "12341234", "testEmail10@test.com", UserGroup.customer);
		UserGroup.addToGroup(testUser2);
		log.info(testUser2);
		testAccount2 = accountDao.createAccount(testUser2);
		testUser2.addAccount(testAccount2);
		log.info(testAccount2.toString());

		testTran = new Transfer(testAccount, testAccount2, 10.0);

	}

	@After
	public void tearDownUser() {
	}

	@Test
	public void test() {
		// make a transaction
		for (int i = 0; i < 11; i++) {
			int transaction_id = tranDao.executeTransfer(testTran);
			if (transaction_id > 0) {
				log.info("It worked!!");
			} else {
				log.info("Failed!");
			}
		}
		tranDao.getTransactionsByUser(testUser).stream().forEach(tran -> log.info(tran));
		log.info(accountDao.getUserAccounts(testUser));
		log.info(accountDao.getUserAccounts(testUser2));

	}
}
