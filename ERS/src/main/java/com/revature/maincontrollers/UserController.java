package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserController {
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	public void seeAccount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;

	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
