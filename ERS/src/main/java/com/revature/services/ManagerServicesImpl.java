package com.revature.services;

import java.sql.Date;
import java.util.List;
import java.util.logging.LogManager;

import com.revature.dao.DaoFactory;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;

public class ManagerServicesImpl implements ManagerServices {
	private static ReimbursementDao rdao = DaoFactory.getDaoFactory().getReimDaoImpl();
	private static UserDao udao = DaoFactory.getDaoFactory().getUserDaoImpl();

	@Override
	public Integer manageReim(Reimbursement reim, User manager, Status status) {
		// TODO Auto-generated method stub
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		reim.setResolver(manager);
		reim.setStatus(status);
		reim.setResolved(date);
		return rdao.update(reim);
	}

	@Override
	public List<Reimbursement> viewPendingReims() {
		// TODO Auto-generated method stub
		List<Reimbursement> allReims = rdao.getAll();
		allReims.removeIf(r -> r.getStatus() != Status.PENDING);

		return allReims;
	}

	@Override
	public List<Reimbursement> viewResolvedReims() {
		// TODO Auto-generated method stub
		List<Reimbursement> allReims = rdao.getAll();
		allReims.removeIf(reim -> reim.getStatus() == Status.PENDING);
		return allReims;
	}

	@Override
	public byte[] viewReceipt(Reimbursement reim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> viewAllEmployees() {
		// TODO Auto-generated method stub
		List<User> employees = udao.getAll();
		System.out.println("View all Employees Dao: " + employees);

		employees.removeIf(u -> u.getRole() != Role.EMPLOYEE);
		System.out.println("View all Employees method: " + employees);
		return employees;
	}

	@Override
	public List<Reimbursement> viewEmployeeReims(User employee) {
		// TODO Auto-generated method stub
		List<Reimbursement> employeeReims = rdao.getAll();
		employeeReims.removeIf(reim -> !reim.getAuthor().equals(employee));
		return employeeReims;
	}

}
