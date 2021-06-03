package com.revature.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReimDaoTest {
	private static Logger log = LogManager.getRootLogger();
	private static UserDao uDao;
	private static User employee, manager;
	private static ReimbursementDao rDao;
	private static Reimbursement reim;
	private static Reimbursement reim2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		uDao = DaoFactory.getDaoFactory().getUserDaoImpl();
		rDao = DaoFactory.getDaoFactory().getReimDaoImpl();
		employee = uDao.getById(1);
		manager = uDao.getById(2);
		reim = new Reimbursement();
		reim.setAmount(40.00);
		reim.setDescription("a Test reimbursement");
		reim.setAuthor(employee);
		reim.setStatus(Status.PENDING);
		reim.setType(ReimType.FOOD);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	// add reim
	@Test
	public void aTest() {
		reim = rDao.add(reim);
		assertTrue(reim.getId() > 0);
	}

	// getById
	@Test
	public void bTest() {
		reim2 = rDao.getById(reim.getId());
		assertEquals(reim.getAuthor(), reim2.getAuthor());
	}

	// get all
	@Test
	public void baTest() {
		reim2 = new Reimbursement();
		reim2 = reim.clone();
		reim2.setType(ReimType.LODGING);
		reim2.setDescription("Generic Business Trip Lodging expenses");
		reim2 = rDao.add(reim2);

		List<Reimbursement> actual = rDao.getAll();
		// actual.stream().forEach(a -> log.debug("getAll() test - " + a));
		assertTrue(actual.size() > 0);
	}

	// update
	@Test
	public void cTest() {
		reim.setDescription("Another description");
		int affected = rDao.update(reim);
		assertTrue(affected > 0);
	}

	// update with manager
	@Test
	public void caTest() {
		reim.setResolver(manager);
		int affected = rDao.update(reim);
		assertTrue(affected > 0);
	}

	// delete
	@Test
	public void dTest() {
		int affected = rDao.delete(reim);
		int affected2 = rDao.delete(reim2);
		assertTrue(affected > 0 && affected2 > 0);
	}
}
