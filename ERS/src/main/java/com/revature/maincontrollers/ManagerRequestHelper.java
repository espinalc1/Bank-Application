package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerRequestHelper {
	private static ManagerController mc = ManagerControllerImpl.getInstance();

	private ManagerRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getRequestURI().substring((request.getContextPath() + "/manager").length());
		PrintWriter pw = response.getWriter();

		System.out.println("NEWEST SYSTEM UPDATE - MANAGER REQUEST HELPER");

		if (path.startsWith("/pendingReims")) {
			mc.viewPendingReims(request, response); // HARDCODED - FIX
		} else if (path.startsWith("/resolvedReims")) {
			mc.viewResolvedReims(request, response);
		} else if (path.startsWith("/allEmployees")) {
			mc.viewEmployees(request, response);
		} else if (path.startsWith("/employeeReims")) {
			mc.viewEmployeeReims(request, response); // HARDCODED - FIX
		} else if (path.startsWith("/acceptReim")) {
			mc.acceptReim(request, response); // HARDCODED - FIX
		} else if (path.startsWith("/rejectReim")) {
			mc.rejectReim(request, response); // HARDCODED - FIX
		} else {
			response.setStatus(404);
		}

	}

}
