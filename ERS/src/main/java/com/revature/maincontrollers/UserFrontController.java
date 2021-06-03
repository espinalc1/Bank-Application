package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFrontController {
	private UserFrontController() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getRequestURI().substring((request.getContextPath() + "/user").length());
		if (path.length() == -1) {
		} else {
			UserRequestHelper.process(request, response);
		}
	}
}
