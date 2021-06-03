package com.cryptobank.DAO;

import java.util.List;

import com.cryptobank.models.BankAccount;
import com.cryptobank.models.User;

public interface AccountDAO {
	public BankAccount createAccount(User user);
	// get user
	// update user details

	public boolean checkAvailable(String field, String value);

	public BankAccount getAccountById(Integer id);

	public BankAccount getAccountByAccountNumber(Integer account_number);
	
	// get accounts by user id - return a list
	public List<BankAccount> getUserAccounts(User user);

	public List<BankAccount> getAllActiveAccounts();

	public List<BankAccount> getAllPendingAccounts();

	public int approveAccountById(Integer id);

	public int approveAccountByAccountNumber(Integer account_number);
	
	public int deleteAccountById(Integer id);
	
	public int deleteAccountByAccountNumber(Integer account_number);
}
