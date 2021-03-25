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

public class TransferTest2 {
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
		testUser = User.getUser("testUser3", "12341234");
		log.info(testUser);
		testAccount = accountDao.getUserAccounts(testUser).get(0);

		testUser2 = User.getUser("testUser4", "12341234");
		log.info(testUser2);
		testAccount2 = accountDao.getUserAccounts(testUser2).get(0);

		testTran = new Transfer(testAccount, testAccount2, 20.0);
		testTran.setTransaction_id(Transfer.makeTransaction(testTran));
	}

	@After
	public void tearDownUser() {

		/*
		 * log.info(testUser.getAccounts().get(0).toString()); // first deletes the bank
		 * account - no on delete then cascade
		 * accountDao.deleteAccountById(testUser.getAccounts().get(0).getAccount_id());
		 * // then deletes the user userDao.deleteUser(testUser);
		 * 
		 * log.info(testUser2.getAccounts().get(0).toString()); // first deletes the
		 * bank account - no on delete then cascade
		 * accountDao.deleteAccountById(testUser2.getAccounts().get(0).getAccount_id());
		 * // then deletes the user userDao.deleteUser(testUser2);
		 */
	}

	@Test
	public void test() {
		// make a transaction
		log.info("Transactions complete!\n");
		log.info(testTran instanceof Transaction);
		log.info(testTran instanceof Transfer);

		tranDao.deleteTransactionById(testTran.getTransaction_id());
		log.info("Done with deletion \n");
		
		tranDao.getTransactionsByUser(testUser);
	}
}
