package com.revature.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {
	private static Logger log = LogManager.getRootLogger();
	private static User user;
	private static UserDao userDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user = new User();
		user.setUsername("testuser1");
		user.setEmail("testuser@username.com");
		user.setPassword("12341234");
		user.setFirst_name("FirstName");
		user.setLast_name("LastName");
		user.setRole(Role.EMPLOYEE);
		userDao = DaoFactory.getDaoFactory().getUserDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	// create user
	@Test
	public void aTest() {
		user = userDao.add(user);
		assertTrue(user.getId() > 0);
	}

	// update user
	@Test
	public void bTest() {
		user.setEmail("updateemail@username.com");
		int affected = userDao.update(user);
		assertTrue(affected > 0);
	}

	// getUserById
	@Test
	public void cTest() {
		User user2 = userDao.getById(user.getId());
		assertEquals(user, user2);
	}

	// delete user
	@Test
	public void dTest() {
		int affected = userDao.delete(user);
		assertTrue(affected > 0);
	}

	@Test
	public void eTest() {
		List<User> users = userDao.getAll();
		System.out.println(users);
		assertTrue(users.size() == 3);
	}

}
