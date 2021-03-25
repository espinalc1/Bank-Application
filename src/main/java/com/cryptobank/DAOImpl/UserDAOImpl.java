package com.cryptobank.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cryptobank.DAO.UserDAO;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;
import com.cryptobank.dbutil.PostgresConnection;

import org.apache.log4j.Logger;

public class UserDAOImpl implements UserDAO {
	private static Logger log = Logger.getLogger(UserDAOImpl.class);

	public UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	// the int is the user id
	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		int k = 0; // tells us if a transaction was successfully applied to the database
		try {

			Connection connection = PostgresConnection.getConnection();

			// put User data in the database
			String sql = "insert into bank_schema.user_info (user_name, password, email) values (?,?,?)";
			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, user.getUserName());
			p.setString(2, user.getPassword());
			p.setString(3, user.getEmail());
			p.executeUpdate(); // p.execute returns an integer, denoting success or failure

			// get the user id after the database
			ResultSet key = p.getGeneratedKeys();
			if (key.next()) {
				k = key.getInt("user_id");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return k;
	}

	@Override
	public boolean checkAvailable(String field_name, String value) {

		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select " + field_name + " from bank_schema.user_info where " + field_name + " = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// select the type of query that I'll be doing
			// it's a result set
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			// do i still have to use next()?
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private User getUserByUniqueField(String field_name, String value) {
		User this_user = null;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select * from bank_schema.user_info where " + field_name + " = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// select the type of query that I'll be doing
			// it's a result set
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			// do i still have to use next()?
			if (rs.next()) {
				this_user = new User();
				this_user.setUserId(rs.getInt("user_id"));
				this_user.setUserName(rs.getString("user_name"));
				this_user.setEmail(rs.getString("email"));
				this_user.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("This user doesn't exist! Please try again.\n");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this_user;
	}

	@Override
	public User getUserByEmail(String user_email) {
		// TODO Auto-generated method stub
		return getUserByUniqueField("email", user_email);
	}

	@Override
	public User getUserByUserName(String user_name) {
		// TODO Auto-generated method stub
		return getUserByUniqueField("user_name", user_name);
	}

	@Override
	public int assignUserToGroup(User user) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int k = 0; // tells us if a transaction was successfully applied to the database
		try {

			Connection connection = PostgresConnection.getConnection();

			// put User data in the database
			String sql = "insert into bank_schema.group_assignments (user_id, group_id) values (?,?)";
			PreparedStatement p = connection.prepareStatement(sql);
			p.setInt(1, user.getUserId());
			p.setInt(2, user.getGroup().getUserGroupId()); // this is autoboxed - check to see if there are problems
			k = p.executeUpdate(); // p.execute returns an integer, denoting success or failure

			// get the user id after the database

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return k;
	}

	@Override
	public UserGroup getUserGroup(User user) {
		// TODO Auto-generated method stub
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "select * from bank_schema.group_assignments where user_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getUserId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				UserGroup this_ug = rs.getInt("group_id") == 1 ? UserGroup.customer : UserGroup.employee;
				return this_ug;

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAllCustomers() {
		// TODO Auto-generated method stub
		List<User> user_list = null;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "" 
					+ "SELECT * FROM " 
					+ "		bank_schema.user_info " 
					+ "INNER JOIN "
					+ "		bank_schema.group_assignments " 
					+ "		ON "
					+ "			user_info.user_id = group_assignments.user_id " 
					+ "INNER JOIN "
					+ "		bank_schema.user_groups " 
					+ "		ON "
					+ "			group_assignments.group_id = user_groups.group_id";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			user_list = new ArrayList<>();

			while (rs.next()) {
				User this_user = new User();
				this_user.setUserId(rs.getInt("user_id"));
				this_user.setUserName(rs.getString("user_name"));
				this_user.setEmail(rs.getString("email"));
				this_user.setPassword(rs.getString("password"));
				this_user.setGroup(rs.getInt("group_id") == 1 ? UserGroup.customer : UserGroup.employee);
				user_list.add(this_user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("This user doesn't exist! Please try again.");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_list;
	}

	@Override
	public int deleteUser(User user) {
		// TODO Auto-generated method stub
		
		int c = 0;
		try {
			Connection connection = PostgresConnection.getConnection();
			String sql = "delete from bank_schema.user_info where user_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			// dynamically alter this method for less coding
			ps.setInt(1, user.getUserId());
			c = ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

}
