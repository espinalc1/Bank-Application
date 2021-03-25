package com.cryptobank.models;

import java.util.List;

import com.cryptobank.DAO.TransactionDAO;
import com.cryptobank.DAOImpl.TransactionDAOImpl;

public class Transaction {
	private static TransactionDAO tranDao = new TransactionDAOImpl();

	private Integer type = 1;
	private Integer transaction_id;
	private BankAccount account;
	private Double amount;
	private String status = "pending";
	
	public Transaction() {
		
	}
	
	public Transaction(BankAccount account, Double amount) {
		super();
		this.account = account;
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}
	
	public String getTypeString() {
		if (this.type == 1) {
			return "Withdrawal";
		} else if (this.type == 2) {
			return "Transfer";
		}
		return "null";
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount; 
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public static int makeTransaction(Transaction tran) {
		return tranDao.executeWithdrawal(tran);
	}

	public static List<Transaction> getTransactionsByUser(User user) {
		return tranDao.getTransactionsByUser(user);
	}
	
	public static int deleteUsersTransactions(User user) {
		return tranDao.deleteUsersTransactions(user);
	}

	@Override
	public String toString() {
		return "[type= " + getTypeString() + ", transaction_id=" + transaction_id + ", account=" + account.getAccount_id() + ", amount="
				+ amount + ", status=" + status + "]";
	}
	
}
