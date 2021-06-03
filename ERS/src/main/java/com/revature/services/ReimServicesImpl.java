package com.revature.services;

import java.util.List;

import com.revature.dao.DaoFactory;
import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ReimServicesImpl implements ReimServices {
    public static ReimbursementDao rdao = DaoFactory.getDaoFactory().getReimDaoImpl();

    @Override
    public Reimbursement add(Reimbursement t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reimbursement getById(Integer id) {
        // TODO Auto-generated method stub
        return rdao.getById(id);
    }

    @Override
    public Integer update(Reimbursement t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer delete(Reimbursement t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Reimbursement> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
