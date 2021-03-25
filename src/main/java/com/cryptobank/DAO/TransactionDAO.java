package com.cryptobank.DAO;

import java.util.List;

import com.cryptobank.models.Transaction;
import com.cryptobank.models.Transfer;
import com.cryptobank.models.User;

public interface TransactionDAO {

	public int executeWithdrawal(Transaction tran);

	public int executeTransfer(Transfer tran);

	public int deleteTransaction(Transaction tran);

	// this could be problematic since there are two types of transactions
	// along with it's child class
	public List<Transaction> getTransactionsByUser(User user);

	public int deleteUsersTransactions(User user);

	public int deleteTransactionById(Integer trans_id);

	public List<Transfer> getTransfersByUser(User user);

	public Transfer getTransferById(int transfer_number);

	public int changeTransferStatus(Transfer tran, String status);

}
