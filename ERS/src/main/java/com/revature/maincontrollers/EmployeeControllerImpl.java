package com.revature.maincontrollers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reim;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.EmployeeServices;
import com.revature.services.EmployeeServicesImpl;
import com.revature.services.ReimServices;
import com.revature.services.ReimServicesImpl;
import com.revature.services.UserServices;
import com.revature.services.UserServicesImpl;

public class EmployeeControllerImpl implements EmployeeController {
    private static final long serialVersionUID = 1L;
    private static EmployeeController ec = new EmployeeControllerImpl();
    private static EmployeeServices es = new EmployeeServicesImpl();
    private static UserServices us = new UserServicesImpl();
    private static ObjectMapper om = new ObjectMapper();
    private static ReimServices rm = new ReimServicesImpl();

    private EmployeeControllerImpl() {
    }

    public static EmployeeController getInstance() {
        return ec;
    }

    @Override
    public void requestReim(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        // HARDCODED
        User employee = us.getById(((User) request.getSession().getAttribute("user")).getId());

        // System.out.println("StringBuilder data: " + sb.toString());
        ObjectMapper om = new ObjectMapper();
        Reim reimJSON = om.readValue(sb.toString(), Reim.class);

        Reimbursement reim = new Reimbursement();
        reim.setAuthor(employee);
        reim.setDescription(reimJSON.getDescription());
        reim.setAmount(Double.parseDouble(reimJSON.getAmount()));

        switch (reimJSON.getType()) {
            case "lodging":
                reim.setType(ReimType.LODGING);
                break;
            case "food":
                reim.setType(ReimType.FOOD);
                break;
            case "travel":
                reim.setType(ReimType.TRAVEL);
                break;
            default:
                reim.setType(ReimType.OTHER);
                break;
        }

        reim = es.submitReim(reim);
        response.setStatus(200);
    }

    @Override
    public void viewPendingReims(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub

        User employee = us.getById(((User) request.getSession().getAttribute("user")).getId());
        List<Reimbursement> reims = es.getPendingReims(employee);
        if (reims == null) {
            // pw.write("nada");
            response.setStatus(404);
        } else {
            String json = om.writeValueAsString(reims);
            response.setStatus(200);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json);
        }

    }

    @Override
    public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub

        // HARDCODED - should get token for this
        User employee = us.getById(((User) request.getSession().getAttribute("user")).getId());
        List<Reimbursement> reims = es.getResolvedReims(employee);
        if (reims == null) {
            // pw.write("nada");
            response.setStatus(404);
        } else {
            String json = om.writeValueAsString(reims);
            response.setStatus(200);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json);
        }

    }

    @Override  // mot in use - go to USER CONTROLLER FOR EMPLOYEE UPDATE CODE
    public void updateAccountInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        // HARDCODED - FIXED
        User employee = us.getById(((User) request.getSession().getAttribute("user")).getId());

        ObjectMapper om = new ObjectMapper();
        User userJSON = om.readValue(sb.toString(), User.class);

        String username = userJSON.getUsername();
        String password = userJSON.getPassword();
        String email = userJSON.getEmail();

        if (username.equals("") || username != null) {
            employee.setUsername(username);
        }

        if (email.equals("") || email != null) {
            employee.setEmail(email);
        }

        if (password.equals("") || password != null) {
            employee.setPassword(password);
        }

        int successful = es.updateInfo(employee);
        if (successful > 0) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }

    }

}
