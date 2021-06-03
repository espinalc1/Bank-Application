package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRequestHelper {
	private UserRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// this should direct it to the appropriate place
		// will get a URI

		String path = request.getRequestURI().substring((request.getContextPath() + "/user").length());
		PrintWriter pw = response.getWriter();
		// pw.write("<h1> Reached User Request Helper </h1>");
		// pw.write("<h2> Context Path: " + path + " </h2>");
		// pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");
		// user
		// session - if there's no session then move to register or login

		if (path.startsWith("/login")) { // i want to redirect this login
			// pw.write("<h2> Going to login page </h2>");
			UserControllerImpl.getInstance().login(request, response);

		} else if (path.startsWith("/logout")) {
			System.out.println("reached logout page");
			UserControllerImpl.getInstance().logout(request, response);

		} else if (path.startsWith("/register")) {
			// pw.write("<h2> Going to Register Page </h2>");
			UserControllerImpl.getInstance().register(request, response);

		} else if (path.startsWith("/account")) {
			// pw.write("<h2> Going to Account Management Page </h2>");
			UserControllerImpl.getInstance().seeAccount(request, response);

		} else if (path.startsWith("/update")) {
			// pw.write("<h2> Going to Account Management Page </h2>");
			UserControllerImpl.getInstance().updateAccount(request, response);
		}

	}
}
