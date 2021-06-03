package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;

public interface ManagerServices {
	public Integer manageReim(Reimbursement reim, User manager, Status status);

	public List<Reimbursement> viewPendingReims();

	public List<Reimbursement> viewResolvedReims();

	public byte[] viewReceipt(Reimbursement reim);

	public List<User> viewAllEmployees();

	public List<Reimbursement> viewEmployeeReims(User employee);
}
