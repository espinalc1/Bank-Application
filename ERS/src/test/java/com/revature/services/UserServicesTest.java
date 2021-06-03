package com.revature.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesTest {
	@Mock
	UserDao uDao = new UserDaoImpl();

	@InjectMocks
	UserServices uServices = new UserServicesImpl();

	@Test
	public void aTest() {
		List<User> users = uServices.getAll();
		assertTrue(users.size() > 0);
	}

}
