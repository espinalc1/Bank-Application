package com.revature.maincontrollers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserServices;
import com.revature.services.UserServicesImpl;

public class UserControllerImpl implements UserController {
	private static UserController uc = new UserControllerImpl();
	private static ViewDelegate vd = new ViewDelegate();
	private static UserServices us = new UserServicesImpl();
	private static ObjectMapper om = new ObjectMapper();

	private UserControllerImpl() {
	}

	public static UserController getInstance() {
		return uc;
	}

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method = request.getMethod();
		PrintWriter pw = response.getWriter();

		switch (method) {
			case "GET":
				vd.processView(request, response);
				break;

			case "POST":
				/*
				 * StringBuilder sb = new StringBuilder(); BufferedReader reader =
				 * request.getReader(); try { String line; while ((line = reader.readLine()) !=
				 * null) { sb.append(line).append('\n'); } } finally { reader.close(); }
				 * 
				 * System.out.println("StringBuilder data: " + sb); ObjectMapper om = new
				 * ObjectMapper(); UserLogger ul = om.readValue(sb.toString(),
				 * UserLogger.class);
				 * 
				 * System.out.println("username: " + ul.getUsername() + ": password: " +
				 * ul.getPassword()); String username = ul.getUsername(); String password =
				 * ul.getPassword();
				 * 
				 * User user = us.loginByUsername(username, password);
				 */

				System.out.println("LATEST SYSTEM UPDATE - USER CONTROLLER IMPL");

				HttpSession session = request.getSession(false);
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				// System.out.println("username: " + username + ": password: " + password);
				User user = us.loginByUsername(username, password);

				if (user != null) {
					session = request.getSession();
					session.setAttribute("role", user.getRole().toString());
					session.setAttribute("user", user);
					response.setStatus(200);

					String role = (String) session.getAttribute("role");
					switch (role) {
						case "MANAGER":
							response.sendRedirect("/ERS/manager");
							break;
						case "EMPLOYEE":
							response.sendRedirect("/ERS/employee");
							break;
						default:
							System.out.println("NEWEST SYSTEM UPDATE - NON USER REDIRECT");
							response.sendRedirect("/ERS");
							break;
					}
				} else {
					response.setStatus(401);
				}
				break;
			case "DELETE":
				break;
			default:
				break;
		}

	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getMethod().equals("POST")) {
			try {
				HttpSession session = request.getSession(false);
				session.invalidate();
			} catch (NullPointerException e) {
			} finally {
				response.setStatus(200);
				response.sendRedirect("http://localhost:8080/ERS/user/login");
			}
		}
	}

	@Override
	public void register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void seeAccount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		User user = us.getById(((User) request.getSession().getAttribute("user")).getId());
		if (user == null) {
			response.setStatus(404);
		} else { // POOR CODING QUALITY
			List<User> fakeArray = new ArrayList<>(); // not for iteration. Just for compatibility with JS Function
													// which expects a list
			fakeArray.add(user);
			String json = om.writeValueAsString(fakeArray);
			response.getWriter().write(json);
		}

	}

	@Override // HARDCODED - FIXED
	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}

		User employee = us.getById(((User) request.getSession().getAttribute("user")).getId());

		User userJSON = om.readValue(sb.toString(), User.class);

		String username = userJSON.getUsername();
		String password = userJSON.getPassword();
		String email = userJSON.getEmail();

		if (!username.equals("") && (username != null)) {
			employee.setUsername(username);
		}

		if (!email.equals("") && (email != null)) {
			employee.setEmail(email);
		}

		if (!password.equals("") && (password != null)) {
			employee.setPassword(password);
		}

		int successful = us.update(employee);

		if (successful > 0) {
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}

	}

}
