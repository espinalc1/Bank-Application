package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface EmployeeServices {
	public Reimbursement submitReim(Reimbursement reim);

	public Integer uploadReceipt(Reimbursement reim, byte[] receipt);

	public List<Reimbursement> getPendingReims(User employee);

	public List<Reimbursement> getResolvedReims(User employee);

	public User viewUserInfo(User employee);

	public Integer updateInfo(User employee);
}
