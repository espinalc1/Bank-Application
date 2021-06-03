package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dbutils.PostgresConnection;
import com.revature.models.Role;
import com.revature.models.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl implements UserDao {
	public static Logger log = LogManager.getRootLogger();
	public static Connection c;

	@Override
	public User add(User t) {
		// TODO Auto-generated method stub
		// sql
		// try a connection
		// prepared statements
		// result set
		// generated id key
		String sql = "INSERT INTO ers_schema.users (username, password, first_name, last_name, email, role_id) VALUES (?, ?, ?, ?, ?, ?);";

		try {
			c = PostgresConnection.getConnection();
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirst_name());
			ps.setString(4, t.getLast_name());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().id());

			int affected = ps.executeUpdate();
			if (affected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				t.setId(rs.getInt("id"));
				c.commit();
			} else {
				c.rollback();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return t;
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "SELECT * FROM ers_schema.users WHERE id = ?";
		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user.setId(id);
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				Integer role_id = rs.getInt("role_id");
				switch (role_id) {
					case 1:
						user.setRole(Role.EMPLOYEE);
						break;
					case 2:
						user.setRole(Role.MANAGER);
						break;
					case 3:
						user.setRole(Role.FINANCE_MANAGER);
						break;
					default:
						user.setRole(Role.EMPLOYEE);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public int update(User t) {
		String sql = "UPDATE ers_schema.users SET username = ?, password = ?, first_name = ?, last_name = ?, email = ?, role_id = ? WHERE id = ?;";

		try {
			c = PostgresConnection.getConnection();
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirst_name());
			ps.setString(4, t.getLast_name());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().id());
			ps.setInt(7, t.getId());

			int affected = ps.executeUpdate();
			if (affected > 0) {
				c.commit();
				return affected;
			} else {
				c.rollback();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public int delete(User t) {
		String sql = "DELETE FROM ers_schema.users WHERE id = ?;";

		try {
			c = PostgresConnection.getConnection();
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setInt(1, t.getId());

			int affected = ps.executeUpdate();
			if (affected > 0) {
				c.commit();
				return affected;
			} else {
				c.rollback();
				return affected;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM ers_schema.users";
		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				Integer role_id = rs.getInt("role_id");
				switch (role_id) {
					case 1:
						user.setRole(Role.EMPLOYEE);
						break;
					case 2:
						user.setRole(Role.MANAGER);
						break;
					case 3:
						user.setRole(Role.FINANCE_MANAGER);
						break;
					default:
						user.setRole(Role.EMPLOYEE);
				}
				users.add(user);
				log.debug(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "SELECT * FROM ers_schema.users WHERE email = ?";
		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				Integer role_id = rs.getInt("role_id");
				switch (role_id) {
					case 1:
						user.setRole(Role.EMPLOYEE);
						break;
					case 2:
						user.setRole(Role.MANAGER);
						break;
					case 3:
						user.setRole(Role.FINANCE_MANAGER);
						break;
					default:
						user.setRole(Role.EMPLOYEE);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User getByUsername(String username) {
		User user = new User();
		String sql = "SELECT * FROM ers_schema.users WHERE username = ?";
		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				Integer role_id = rs.getInt("role_id");
				switch (role_id) {
					case 1:
						user.setRole(Role.EMPLOYEE);
						break;
					case 2:
						user.setRole(Role.MANAGER);
						break;
					case 3:
						user.setRole(Role.FINANCE_MANAGER);
						break;
					default:
						user.setRole(Role.EMPLOYEE);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

}
