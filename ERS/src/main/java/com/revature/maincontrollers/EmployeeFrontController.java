package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeFrontController {
	private static EmployeeFrontController efc = new EmployeeFrontController();

	private EmployeeFrontController() {
	}

	public static EmployeeFrontController getInstance() {
		return efc;
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// all of the user requests will go through here
		String path = request.getRequestURI().substring((request.getContextPath() + "/employee").length());
		// PrintWriter pw = response.getWriter();
		// pw.write("<h1> Reached Manager Front Controller </h1>");
		// pw.write("<h2> Path: " + path + " </h2>");
		// pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");

		// should then send to the user request helper
		// perhaps can include a if check statement to make other checks
		if (path.equals("") || path.equals("/")) {
			ViewDelegate.getInstance().processView(request, response);
		} else {
			EmployeeRequestHelper.process(request, response);
		}
	}

}
