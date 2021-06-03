package com.revature.services;

import java.util.List;

import com.revature.dao.DaoFactory;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;

public class EmployeeServicesImpl implements EmployeeServices {
    private static ReimbursementDao rdao = DaoFactory.getDaoFactory().getReimDaoImpl();
    private static UserDao udao = DaoFactory.getDaoFactory().getUserDaoImpl();

    @Override
    public Reimbursement submitReim(Reimbursement reim) {
        // TODO Auto-generated method stub
        return rdao.add(reim);
    }

    @Override
    public Integer uploadReceipt(Reimbursement reim, byte[] receipt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Reimbursement> getPendingReims(User employee) {
        // TODO Auto-generated method stub
        List<Reimbursement> reims = rdao.getAll();
        reims.removeIf(r -> r.getStatus() != Status.PENDING);
        reims.removeIf(r -> !r.getAuthor().equals(employee));
        return reims;
    }

    @Override
    public List<Reimbursement> getResolvedReims(User employee) {
        // TODO Auto-generated method stub
        List<Reimbursement> reims = rdao.getAll();
        reims.removeIf(r -> r.getStatus() == Status.PENDING);
        reims.removeIf(r -> !r.getAuthor().equals(employee));
        return reims;
    }

    @Override
    public User viewUserInfo(User employee) {
        // TODO Auto-generated method stub
        return udao.getById(employee.getId());
    }

    @Override
    public Integer updateInfo(User employee) {
        // TODO Auto-generated method stub
        return udao.update(employee);
    }
}
