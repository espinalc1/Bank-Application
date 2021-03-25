package com.cryptobank.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.TransactionDAO;
import com.cryptobank.dbutil.PostgresConnection;
import com.cryptobank.models.BankAccount;
import com.cryptobank.models.Transaction;
import com.cryptobank.models.Transfer;
import com.cryptobank.models.User;
import com.cryptobank.models.exceptions.TransactionException;
import com.cryptobank.models.validators.Validator;

public class TransactionDAOImpl implements TransactionDAO {
	private static Logger log = Logger.getLogger(TransactionDAOImpl.class);

	public TransactionDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int executeWithdrawal(Transaction tran) {
		// TODO Auto-generated method stub
		Validator validator = new Validator("You don't have enough money in this account, mate!");

		int c = 0;
		try {
			validator.validateBalance(tran);

			Connection connection = PostgresConnection.getConnection();
			connection.setAutoCommit(false);

			// account verification

			String sql_verify = "update bank_schema.accounts set account_balance = account_balance - ? where account_id = ?";
			PreparedStatement p1 = connection.prepareStatement(sql_verify);
			p1.setDouble(1, tran.getAmount());
			p1.setInt(2, tran.getAccount().getAccount_id());
			c = p1.executeUpdate();

			// transaction creation
			String sql_transact = "insert into bank_schema.transactions (type_id, account_id, amount, status) values (?, ?, ?, ?)";
			PreparedStatement p2 = connection.prepareStatement(sql_transact, Statement.RETURN_GENERATED_KEYS);

			p2.setInt(1, tran.getType());
			p2.setInt(2, tran.getAccount().getAccount_id());
			p2.setDouble(3, tran.getAmount());
			p2.setString(4, tran.getStatus());

			c = p2.executeUpdate(); // p.execute returns an integer, denoting success or failure

			// get the user id after the database
			ResultSet key = p2.getGeneratedKeys();
			if (key.next()) {
				tran.setTransaction_id(key.getInt("transaction_id"));
			}

			connection.commit();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (TransactionException e) {
			log.info("Insufficient Funds!");
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("Insufficient Funds!");
			return 0;
		}
		return c;
	}

	// returns the transaction id
	@Override
	public int executeTransfer(Transfer tran) {
		// TODO Auto-generated method stub
		Validator validator = new Validator("You don't have enough money in this account, mate!\n");

		int c = 0;
		try {
			validator.validateBalance(tran);

			Connection connection = PostgresConnection.getConnection();
			connection.setAutoCommit(false);

			// account verification

			String sql_verify = "update bank_schema.accounts set account_balance = account_balance - ? where account_id = ?";
			PreparedStatement p1 = connection.prepareStatement(sql_verify);
			p1.setDouble(1, tran.getAmount());
			p1.setInt(2, tran.getAccount().getAccount_id());
			p1.addBatch();

			p1.setDouble(1, -1.0d * tran.getAmount());
			p1.setInt(2, tran.getReceiver().getAccount_id());
			p1.addBatch();

			p1.executeBatch();

			// transaction creation
			String sql_transact = "insert into bank_schema.transactions (type_id, account_id, receiver_id, amount, status) values (?, ?, ?, ?, ?)";
			PreparedStatement p2 = connection.prepareStatement(sql_transact, Statement.RETURN_GENERATED_KEYS);

			p2.setInt(1, tran.getType());
			p2.setInt(2, tran.getAccount().getAccount_id());
			p2.setInt(3, tran.getReceiver().getAccount_id());
			p2.setDouble(4, tran.getAmount());
			p2.setString(5, tran.getStatus());

			p2.executeUpdate(); // p.execute returns an integer, denoting success or failure

			// if c is greater than zero then that means it has produced a transaction id
			ResultSet key = p2.getGeneratedKeys();
			if (key.next()) {
				c = key.getInt("transaction_id");
				log.debug("executeTransfer - TransferDAOImpl - Transaction ID key = " + c);
			}

			connection.commit();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (TransactionException e) {
			log.info("Insufficient Funds!");
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("Insufficient Funds!");
			return 0;
		}
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public int deleteTransaction(Transaction testTran) {
		// TODO Auto-generated method stub
		log.debug("DeleteTransaction Method " + testTran);
		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();

			// account verification
			String sql = "delete from bank_schema.transactions where transaction_id = ?";
			PreparedStatement p1 = connection.prepareStatement(sql);
			log.debug("DeleteTransaction Method - transaction Id = " + testTran.getTransaction_id());
			p1.setInt(1, testTran.getTransaction_id());

			c = p1.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			return 0;
		} catch (TransactionException e) {
			log.info("Transaction issue");
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.debug(e.getMessage());
			return 0;
		}
		// TODO Auto-generated method stub
		return c;

	}

	@Override
	public int deleteTransactionById(Integer trans_id) {
		// TODO Auto-generated method stub
		log.debug("DeleteTransactionById Method " + trans_id);
		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();

			// account verification
			String sql = "delete from bank_schema.transactions where transaction_id = ?";
			PreparedStatement p1 = connection.prepareStatement(sql);
			log.debug("DeleteTransactionById Method - transaction Id = " + trans_id);
			p1.setInt(1, trans_id);

			c = p1.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			return 0;
		} catch (TransactionException e) {
			log.info("Transaction issue");
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.debug(e.getMessage());
			return 0;
		}
		// TODO Auto-generated method stub
		return c;

	}

	@Override
	public int deleteUsersTransactions(User user) {

		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();

			// account verification

			String sql = "delete from bank_schema.transactions t where t.transaction_id in "
					+ "(select t.transaction_id from " + "bank_schema.transactions t "
					+ "inner join bank_schema.accounts a " + "on t.account_id = a.account_id "
					+ "inner join bank_schema.user_info u " + "on a.user_id = u.user_id " + "where a.user_id = ?) ";
			PreparedStatement p1 = connection.prepareStatement(sql);
			p1.setInt(1, user.getUserId());
			c = p1.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("Insufficient Funds!");
		}
		return c;
	}

	@Override
	public List<Transaction> getTransactionsByUser(User user) {
		// TODO Auto-generated method stub
		List<Transaction> trans = null;
		try {
			Connection connection = PostgresConnection.getConnection();

			// account verification

			String sql = "" + " select * from " + "		bank_schema.transactions t " + "	inner join "
					+ "		bank_schema.accounts a " + "			on t.account_id = a.account_id " + "	inner join "
					+ "		bank_schema.user_info u " + "			on a.user_id = u.user_id " + " where a.user_id = ?";

			PreparedStatement p1 = connection.prepareStatement(sql);
			p1.setInt(1, user.getUserId());
			ResultSet rs = p1.executeQuery();

			// transaction creation
			trans = new ArrayList<>();
			while (rs.next()) {
				Transaction tran = new Transaction();
				tran.setTransaction_id(rs.getInt("transaction_id"));
				tran.setType(rs.getInt("type_id"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setStatus(rs.getString("status"));

				BankAccount ba = new BankAccount();
				ba.setAccount_id(rs.getInt("account_id"));
				ba.setAccount_number(rs.getInt("account_number"));
				ba.setAccount_balance(rs.getDouble("account_balance"));
				ba.setAccount_status(rs.getBoolean("account_status"));
				ba.setAccount_user(user);

				tran.setAccount(ba);

				trans.add(tran);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("Insufficient Funds!");
		}
		return trans;
	}

	@Override
	public List<Transfer> getTransfersByUser(User user) {
		// TODO Auto-generated method stub
		List<Transfer> trans = null;
		try {
			Connection connection = PostgresConnection.getConnection();

			// account verification

			String sql = "select * from \r\n" + "	bank_schema.transactions t \r\n"
					+ "inner join bank_schema.accounts a \r\n" + "	on t.receiver_id = a.account_id \r\n"
					+ "inner join bank_schema.user_info u \r\n" + "	on a.user_id = u.user_id \r\n"
					+ "where a.user_id = ? and t.type_id = 1 and t.status = 'pending';\r\n" + "";

			PreparedStatement p1 = connection.prepareStatement(sql);
			p1.setInt(1, user.getUserId());
			ResultSet rs = p1.executeQuery();

			// transaction creation
			trans = new ArrayList<>();
			while (rs.next()) {
				Transfer tran = new Transfer();
				tran.setTransaction_id(rs.getInt("transaction_id"));
				tran.setType(rs.getInt("type_id"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setStatus(rs.getString("status"));

				BankAccount sender = new BankAccount();
				sender.setAccount_id(rs.getInt("account_id"));
				sender.setAccount_number(rs.getInt("account_number"));
				sender.setAccount_balance(rs.getDouble("account_balance"));
				sender.setAccount_status(rs.getBoolean("account_status"));
				sender.setAccount_user(user);

				BankAccount receiver = new BankAccount();
				receiver.setAccount_id(rs.getInt("receiver_id"));
				receiver.setAccount_number(rs.getInt("account_number"));
				receiver.setAccount_balance(rs.getDouble("account_balance"));
				receiver.setAccount_status(rs.getBoolean("account_status"));
				receiver.setAccount_user(user);

				tran.setAccount(sender);
				tran.setReceiver(receiver);

				log.debug("\ngetTransactionsByUser Method " + tran + "\n");
				trans.add(tran);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.debug("TransactionDAOImpl sql statement problem");
		}
		return trans;
	}

	@Override
	public Transfer getTransferById(int transfer_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int changeTransferStatus(Transfer tran, String status) {
		int c = 0;
		log.debug("ChangeTransferStatus method - " + tran);
		try {
			Connection connection = PostgresConnection.getConnection();
			connection.setAutoCommit(false);

			// update transaction status
			String sql = "update bank_schema.transactions set status = ? where transaction_id = ?";
			PreparedStatement p1 = connection.prepareStatement(sql);

			// update account balance
			String sql2 = "update bank_schema.accounts set account_balance = account_balance + ? where account_id = ?";
			PreparedStatement p2 = connection.prepareStatement(sql2);

			switch (status) {
			case "rejected":
				// Change the status
				p1.setString(1, "rejected");
				p1.setInt(2, tran.getTransaction_id());
				// refund to the user
				p2.setDouble(1, tran.getAmount());
				p2.setInt(2, tran.getAccount().getAccount_id());
				break;
			case "completed":
				// Change the status
				p1.setString(1, "completed");
				p1.setInt(2, tran.getTransaction_id());
				// refund to the user
				p2.setDouble(1, tran.getAmount());
				p2.setInt(2, tran.getReceiver().getAccount_id());
				break;
			}

			c = p1.executeUpdate();
			log.debug(c);
			c = p2.executeUpdate();
			log.debug(c);
			connection.commit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("This failed!");
		}
		return c;

	}
}
