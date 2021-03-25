package com.cryptobank.models;

import java.util.List;

import com.cryptobank.DAO.TransactionDAO;
import com.cryptobank.DAOImpl.TransactionDAOImpl;

public class Transfer extends Transaction {
	private static TransactionDAO tranDao = new TransactionDAOImpl();
	private BankAccount receiver;
	private Integer type = 2;

	public Transfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transfer(BankAccount account, BankAccount receiver, Double amount) {
		super(account, amount);
		// TODO Auto-generated constructor stub
		this.receiver = receiver;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BankAccount getReceiver() {
		// TODO Auto-generated method stub
		return this.receiver;
	}

	public void setReceiver(BankAccount receiver) {
		// TODO Auto-generated method stub
		this.receiver = receiver;
	}

	public static int makeTransaction(Transfer tran) {
		return tranDao.executeTransfer(tran);
	}

	public static List<Transfer> getTransfersByUser(User user) {
		return tranDao.getTransfersByUser(user);
	}

	public static Transfer getTransferById(int transfer_number) {
		// TODO Auto-generated method stub
		return tranDao.getTransferById(transfer_number);
	}

	public static int changeTransferStatus(Transfer selected_tran, String status) {
		// TODO Auto-generated method stub
		return tranDao.changeTransferStatus(selected_tran, status);
	}

	@Override
	public String toString() {
		return "[transaction_id= " + getTransaction_id() + " amount= " + getAmount() + " receiver= "
				+ receiver.getAccount_user().getUserName() + "]";
	}

}
