package com.cryptobank.models.validators;

import org.apache.log4j.Logger;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.AccountDAOImpl;
import com.cryptobank.DAOImpl.UserDAOImpl;
import com.cryptobank.models.Transaction;
import com.cryptobank.models.exceptions.TransactionException;
import com.cryptobank.models.exceptions.UserException;

public class Validator {
	private static Logger log = Logger.getLogger(Validator.class);

	private String message;
	private String pattern;

	public Validator(String message) {
		super();
		this.message = message;
	}

	public Validator(String message, String pattern) {
		super();
		this.message = message;
		this.pattern = pattern;
	}

	public void validate(String input) throws UserException {
		if (!input.matches(this.pattern)) {
			throw new UserException(this.message);
		} else {
			log.debug("The validation pattern was matched");
		}
	}

	public void validateUnique(String field_name, String value) throws UserException {
		UserDAO uniqueChecker = new UserDAOImpl();

		// could generalize this portion to other things
		boolean unique = uniqueChecker.checkAvailable(field_name, value);
		if (unique) {
			throw new UserException(this.message);
		} else {
			log.debug("The input is unique!");
		}
	}

	public void validateBalance(Transaction tran) throws TransactionException {
		AccountDAO accDao = new AccountDAOImpl();
		if (accDao.getAccountById(tran.getAccount().getAccount_id()).getAccount_balance() - tran.getAmount() < 0) {
			throw new TransactionException(message);
		} else {
			log.debug("This value is okay!");
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
