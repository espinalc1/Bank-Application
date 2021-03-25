package com.cryptobank.models;

import java.util.List;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAOImpl.AccountDAOImpl;

public class BankAccount {
	private static AccountDAO dao = new AccountDAOImpl();

	private Integer account_id;
	private Integer account_number;
	private Double account_balance;
	private User account_user;
	private boolean account_status;
	public BankAccount() {
		super();
	}

	public Integer getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

	public Integer getAccount_number() {
		return account_number;
	}

	public void setAccount_number(Integer account_number) {
		this.account_number = account_number;
	}

	public Double getAccount_balance() {
		return account_balance;
	}

	public void setAccount_balance(double d) {
		this.account_balance = d;
	}

	public User getAccount_user() {
		return account_user;
	}

	public void setAccount_user(User account_user) {
		this.account_user = account_user;
	}

	public boolean account_status() {
		return account_status;
	}

	public void setAccount_status(boolean account_status) {
		this.account_status = account_status;
	}


	public void setAccounts(List<BankAccount> accounts) {
	}

	public static List<BankAccount> getAllPendingAccounts() {
		List<BankAccount> ba = dao.getAllPendingAccounts();
		return ba;
	}
	
	public static List<BankAccount> getAllActiveAccounts(){
		List<BankAccount> ba = dao.getAllActiveAccounts();
		return ba;
	}

	public static List<BankAccount> getUserAccounts(User user) {
		List<BankAccount> user_accounts = dao.getUserAccounts(user);
		return user_accounts;
	}

	public static BankAccount createAccount(User user) {
		return dao.createAccount(user);
	}
	
	public static int approveAccount(Integer account_number) {
		return dao.approveAccountByAccountNumber(account_number);
	}
	
	public static int deleteAccount(Integer account_number) {
		return dao.deleteAccountByAccountNumber(account_number);
	}
	
	public static BankAccount getAccountById(Integer account_number) {
		return dao.getAccountByAccountNumber(account_number);
	}

	@Override
	public String toString() {
		return "[account_number=" + account_number + ", account_balance=" + account_balance
				+ ", account_user=" + account_user.getUserId() + "]";
	}



}
