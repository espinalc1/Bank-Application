package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EmployeeController {
    public void requestReim(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void viewPendingReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void updateAccountInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
