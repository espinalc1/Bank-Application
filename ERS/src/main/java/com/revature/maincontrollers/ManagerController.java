package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ManagerController {
	// dashboard
	public void viewPendingReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void viewReceipts(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void viewEmployeeReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void viewEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void acceptReim(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void rejectReim(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
