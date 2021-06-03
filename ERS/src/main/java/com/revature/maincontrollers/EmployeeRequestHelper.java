package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeRequestHelper {
	private static EmployeeController ec = EmployeeControllerImpl.getInstance();

	private EmployeeRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// this should direct it to the appropriate place
		// will get a URI

		String path = request.getRequestURI().substring((request.getContextPath() + "/employee").length());
		PrintWriter pw = response.getWriter();

		// pw.write("<h1> Reached Manager Helper </h1>");
		// pw.write("<h2> Path: " + path + " </h2>");
		// pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");

		// user
		// session - if there's no session then move to register or login

		if (path.startsWith("/pendingReims")) {
			// pw.write("Reached pending reims");
			ec.viewPendingReims(request, response);
		} else if (path.startsWith("/resolvedReims")) {
			ec.viewResolvedReims(request, response);
		} else if (path.startsWith("/requestReim")) {
			ec.requestReim(request, response);
		} else {
			response.setStatus(404);
		}

	}

}
