package com.revature.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.dao.ReimbursementDao;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ManagerServicesTest {
	public static Logger log = LogManager.getRootLogger();

	private static List<Reimbursement> reims;

	@InjectMocks
	private ManagerServices mServices = new ManagerServicesImpl();

	@Mock
	private ReimbursementDao dao;

	@BeforeClass
	public static void beforeAll() {
		reims = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Reimbursement reim = new Reimbursement();
			reim.setId(i);
			reim.setAmount(100.00);
			reim.setSubmitted(new Date(1000000000000000L));
			reim.setDescription("Test reimbursement" + i);

			User author = new User();
			author.setId(i);
			author.setEmail("email" + i + "@company.com");
			author.setFirst_name("FirstName" + i);
			author.setLast_name("LastName" + i);
			author.setPassword("12341234");
			author.setRole(Role.EMPLOYEE);
			reim.setAuthor(author);

			if (i % 3 == 0) {
				reim.setType(ReimType.FOOD);
			} else if (i % 2 == 0) {
				reim.setType(ReimType.LODGING);
			} else {
				reim.setType(ReimType.OTHER);
			}

			User manager = new User();
			manager.setId(2);
			manager.setEmail("manager@company.com");
			manager.setFirst_name("FirstNameManager");
			manager.setLast_name("LastNameManager");
			manager.setPassword("12341234");
			manager.setRole(Role.MANAGER);

			if (i % 3 == 0) {
				reim.setStatus(Status.APPROVED);
				reim.setResolver(manager);
				reim.setResolved(new Date(1000060000000000L));
			} else if (i % 2 == 0) {
				reim.setStatus(Status.DENIED);
				reim.setResolver(manager);
				reim.setResolved(new Date(1000060000000000L));
			} else {
				reim.setStatus(Status.PENDING);
			}
			reims.add(reim);
		}

		reims.stream().forEach(r -> log.debug(r.getStatus()));

	}

	@Ignore
	@AfterClass
	public static void afterAll() {
		/*
		 * List<Reimbursement> temp = reims; temp.removeIf(r -> r.getStatus() !=
		 * Status.PENDING); temp.stream().forEach(r -> log.debug(r));
		 */

	}

	@Test
	@Ignore
	public void testGetAll() {

	}

}
