package com.cryptobank.frontend;

import java.util.List;
import java.util.Scanner;

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
import com.cryptobank.models.BankAccount;
import com.cryptobank.models.Transfer;
import com.cryptobank.models.User;
import com.cryptobank.models.UserCreateTest;

public class IncomingTransfersDashboardTest {
	private static Logger log = Logger.getLogger(UserCreateTest.class);
	private static UserDAO userDao = new UserDAOImpl();
	private static AccountDAO accountDao = new AccountDAOImpl();
	private static TransactionDAO tranDao = new TransactionDAOImpl();
	private static Scanner sc = new Scanner(System.in);
	private User testUser;
	private User testUser2;
	private BankAccount testAccount;
	private BankAccount testAccount2;
	private Transfer testTran;

	@Before
	public void setUp() {
		// set up users
		testUser = User.getUser("testUser1", "12341234");
//		log.info(testUser);
		testAccount = accountDao.getUserAccounts(testUser).get(0);

		testUser2 = User.getUser("testUser2", "12341234");
//		log.info(testUser2);
		testAccount2 = accountDao.getUserAccounts(testUser2).get(0);
	}
	
	
	@After
	public void tearDown() {

	}

	@Test
	public void test() {
		List<Transfer> trans = tranDao.getTransfersByUser(testUser2);
		trans.stream().forEach(tran -> log.info(tran));
	}
	
	@Test
	public void test2() {
		Dashboard.getDashboard().approveIncomingTransfers(sc, testUser2, testAccount2);
	}

}
