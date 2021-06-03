package com.revature.frontcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.maincontrollers.EmployeeFrontController;
import com.revature.maincontrollers.ManagerFrontController;
import com.revature.maincontrollers.UserFrontController;

public class MainRequestHelper {

	private MainRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// this should direct it to the appropriate place
		// will get a URI
		System.out.println("NEWEST SYSTEM UPDATE - MAIN REQUEST HELPER");

		String path = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession(false);

		if (path.startsWith("/user")) {
			UserFrontController.process(request, response);
		} else if (path.startsWith("/employee")) {
			EmployeeFrontController.getInstance().process(request, response);
		} else if (path.startsWith("/manager")) {
			String role = (String) session.getAttribute("role");
			switch (role) {
				case "MANAGER":
					ManagerFrontController.getInstance().process(request, response);
					break;
				case "EMPLOYEE":
					// if an employee tries to access manager services it will automatically
					// redirect
					response.sendRedirect("/ERS/employee");// System.out.println("not in a dispatcher");
					break;
				default:
					response.sendRedirect("/ERS");// System.out.println("not in a dispatcher");
					break;
			}
		} else {
			response.setStatus(404);
		}

	}
}
