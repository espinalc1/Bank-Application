package com.cryptobank.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cryptobank.DAO.AccountDAO;
import com.cryptobank.DAO.UserDAO;
import com.cryptobank.dbutil.PostgresConnection;
import com.cryptobank.models.BankAccount;
import com.cryptobank.models.User;

import jdk.internal.org.jline.utils.Log;

public class AccountDAOImpl implements AccountDAO {
	public static Logger log = Logger.getLogger(AccountDAOImpl.class);
	@Override
	public BankAccount createAccount(User user) {
		BankAccount account = null;
		try {

			Connection connection = PostgresConnection.getConnection();

			// put User data in the database
			String sql = "insert into bank_schema.accounts (user_id) values (?)";
			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, user.getUserId());
			p.executeUpdate(); // p.execute returns an integer, denoting success or failure

			// get the user id after the database
			ResultSet key = p.getGeneratedKeys();
			if (key.next()) {
				account = new BankAccount();
				account.setAccount_id(key.getInt("account_id"));
				account.setAccount_number(key.getInt("account_number"));
				account.setAccount_user(user);
				account.setAccount_balance(key.getDouble("account_balance"));
				account.setAccount_status(key.getBoolean("account_status"));
			}
 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}

	@Override
	public boolean checkAvailable(String field, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BankAccount> getUserAccounts(User user) {
		// TODO Auto-generated method stub
		List<BankAccount> baList = null;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select * from bank_schema.accounts where user_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getUserId());
			ResultSet rs = ps.executeQuery();
			baList = new ArrayList<>();

			while (rs.next()) {
				BankAccount account = new BankAccount();
				account.setAccount_id(rs.getInt("account_id"));
				account.setAccount_number(rs.getInt("account_number"));
				account.setAccount_user(user);
				account.setAccount_balance(rs.getDouble("account_balance"));
				account.setAccount_status(rs.getBoolean("account_status"));
				baList.add(account);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baList;
	}

	private List<BankAccount> getAllAccounts(boolean status) {
		// TODO Auto-generated method stub
		List<BankAccount> baList = null;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select * from bank_schema.accounts inner join bank_schema.user_info on accounts.user_id = user_info.user_id where account_status = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBoolean(1, status);
			ResultSet rs = ps.executeQuery();
			baList = new ArrayList<>();

			while (rs.next()) {
				BankAccount account = new BankAccount();
				User user = new User();
				account.setAccount_id(rs.getInt("account_id"));
				account.setAccount_number(rs.getInt("account_number"));
				account.setAccount_balance(rs.getDouble("account_balance"));
				account.setAccount_status(rs.getBoolean("account_status"));

				// how to get all of a user's information in this table
				user.setUserId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setUserName(rs.getString("user_name"));

				account.setAccount_user(user);
				baList.add(account);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baList;

	}

	@Override
	public List<BankAccount> getAllActiveAccounts() {
		// TODO Auto-generated method stub
		return getAllAccounts(true);
	}

	@Override
	public List<BankAccount> getAllPendingAccounts() {
		// TODO Auto-generated method stub
		return getAllAccounts(false);
	}

	public int updateAccountByField(String update_field, Object update_value, String unique_id_field,
			Integer unique_id_value) {
		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "update bank_schema.accounts set " + update_field + " = ? where " + unique_id_field + " = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			// dynamically alter this method for less coding
			if (update_value instanceof Boolean) {
				ps.setBoolean(1, (boolean) update_value);
			} else if (update_value instanceof String) {
				ps.setString(1, (String) update_value);
			} else if (update_value instanceof Integer) {
				ps.setInt(1, (Integer) update_value);
			}
			ps.setInt(2, unique_id_value);
			c = ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	private int deleteAccountByField(String unique_id_field, Integer unique_id_value) {
		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "delete from bank_schema.accounts where " + unique_id_field + " = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			// dynamically alter this method for less coding
			ps.setInt(1, unique_id_value);
			c = ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public int approveAccountById(Integer id) {
		return updateAccountByField("account_status", true, "account_id", id);
	}

	@Override
	public int approveAccountByAccountNumber(Integer account_number) {
		return updateAccountByField("account_status", true, "account_number", account_number);
	}

	@Override
	public BankAccount getAccountByAccountNumber(Integer account_number) {
		// TODO Auto-generated method stub
		return getAccountByUniqueField("account_number", account_number);
	}

	@Override
	public BankAccount getAccountById(Integer id) {
		// TODO Auto-generated method stub
		return getAccountByUniqueField("account_id", id);
	}

	public BankAccount getAccountByUniqueField(String unique_id_field, Integer unique_id_value) {
		BankAccount account = null;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select * from bank_schema.accounts a inner join bank_schema.user_info u on a.user_id = u.user_id where " + unique_id_field + " = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
	
			ps.setInt(1, unique_id_value);
			ResultSet rs = ps.executeQuery();
	
			if (rs.next()) {
				account = new BankAccount();
				account.setAccount_id(rs.getInt("account_id"));
				account.setAccount_number(rs.getInt("account_number"));
				account.setAccount_balance(rs.getInt("account_balance"));
				account.setAccount_status(rs.getBoolean("account_status"));
				
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setEmail(rs.getString("email"));
				
				account.setAccount_user(user);
			}
	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public int deleteAccountById(Integer id) {
		// TODO Auto-generated method stub
		return deleteAccountByField("account_id", id);
	}

	@Override
	public int deleteAccountByAccountNumber(Integer account_number) {
		// TODO Auto-generated method stub
		return deleteAccountByField("account_number", account_number);
	}

}
