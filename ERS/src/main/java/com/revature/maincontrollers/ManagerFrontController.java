package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerFrontController {
	private static ManagerFrontController mfc = new ManagerFrontController();

	private ManagerFrontController() {
	}

	public static ManagerFrontController getInstance() {
		return mfc;
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getRequestURI().substring((request.getContextPath() + "/manager").length());
		System.out.println("LATEST SYSTEM UPDATE - MANAGER FRONT CONTROLLER");

		if (path.equals("") || path.equals("/")) {
			ViewDelegate.getInstance().processView(request, response);
		} else {
			ManagerRequestHelper.process(request, response);
		}
	}

}
