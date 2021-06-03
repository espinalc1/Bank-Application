package com.revature.dao;

public class DaoFactory {
	private static DaoFactory dao;

	private DaoFactory() {
	}

	public static DaoFactory getDaoFactory() {
		if (dao == null) {
			dao = new DaoFactory();
			return dao;
		} else {
			return dao;
		}
	}

	public UserDao getUserDaoImpl() {
		return new UserDaoImpl();

	}

	public ReimbursementDao getReimDaoImpl() {
		return new ReimbursementDaoImpl();

	}
}