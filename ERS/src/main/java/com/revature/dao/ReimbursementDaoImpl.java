package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dbutils.PostgresConnection;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;

public class ReimbursementDaoImpl implements ReimbursementDao {
	private static Logger log = LogManager.getRootLogger();
	private static Connection c;

	@Override
	public Reimbursement add(Reimbursement t) {
		/*
		 * 1. employee initiates - add 2. finance manager approves or disapproves -
		 * update 3. is there a way to check for uniqueness?
		 */
		String sql = "INSERT INTO ers_schema.reimbursements (amount, description, receipt, author, rtype) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try {
			c = PostgresConnection.getConnection();
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, t.getAmount());
			ps.setString(2, t.getDescription());
			ps.setBytes(3, t.getReceipt());
			ps.setInt(4, t.getAuthor().getId());
			ps.setInt(5, t.getType().id());

			int affected = ps.executeUpdate();
			if (affected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				t.setId(rs.getInt("id"));
				t.setSubmitted(rs.getDate("submitted"));
				c.commit();
			} else {
				c.rollback();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (c != null || c.isClosed()) {
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
	public Reimbursement getById(Integer id) {
		String sql = "SELECT r.id, r.amount, r.submitted, r.resolved, r.description, "
				+ "		r.receipt, e.id as e_id, e.username as e_username, e.password as e_password, "
				+ "		e.first_name as e_first_name, e.last_name as e_last_name, "
				+ "		e.email as e_email, e.role_id as e_role_id, m.id as m_id, "
				+ "		m.username as m_username, m.password as m_password, m.first_name as m_first_name, "
				+ "		m.last_name as m_last_name, m.email as m_email, m.role_id as m_role_id, "
				+ "		r.status, r.rtype FROM ers_schema.reimbursements r LEFT JOIN ers_schema.users e "
				+ "		ON r.author = e.id LEFT JOIN ers_schema.users m ON r.resolver = m.id " + " WHERE r.id = ?;";

		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Reimbursement reim = new Reimbursement();
				reim.setId(id);
				reim.setAmount(rs.getDouble("amount"));
				reim.setSubmitted(rs.getDate("submitted"));
				reim.setResolved(rs.getDate("resolved"));
				reim.setDescription(rs.getString("description"));
				reim.setReceipt(rs.getBytes("receipt"));

				// lets solve this through a join
				// e stands for employee
				User e = new User();
				e.setId(rs.getInt("e_id"));
				e.setUsername(rs.getString("e_username"));
				e.setFirst_name(rs.getString("e_first_name"));
				e.setLast_name(rs.getString("e_last_name"));
				e.setEmail(rs.getString("e_email"));
				e.setPassword(rs.getString("e_password"));
				e.setRole(getRole(rs.getInt("e_role_id")));

				reim.setAuthor(e);

				User m = new User();
				m.setId(rs.getInt("m_id"));
				m.setUsername(rs.getString("m_username"));
				m.setFirst_name(rs.getString("m_first_name"));
				m.setLast_name(rs.getString("m_last_name"));
				m.setEmail(rs.getString("m_email"));
				m.setPassword(rs.getString("m_password"));
				m.setRole(getRole(rs.getInt("m_role_id")));

				if (m.getEmail() == null) {
					m = null;
				}

				reim.setResolver(m);

				reim.setStatus(getStatus(rs.getInt("status")));
				reim.setType(getType(rs.getInt("rtype")));

				return reim;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int update(Reimbursement t) {
		// TODO Auto-generated method stub
		String add = "";
		if (t.getResolver() != null) {
			add = "resolver = ?, ";
			log.debug("update - t.getResolver(): " + t.getResolver());
		}
		String sql = "UPDATE ers_schema.reimbursements "
				+ "SET amount = ?, submitted = ?, resolved = ?, description = ?, receipt = ?, author = ?, " + add
				+ "status = ?, rtype = ? WHERE id = ?";
		log.debug("update - sql: " + sql);

		try {
			c = PostgresConnection.getConnection();
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDouble(1, t.getAmount());
			ps.setDate(2, t.getSubmitted());
			ps.setDate(3, t.getResolved());
			ps.setString(4, t.getDescription());
			ps.setBytes(5, t.getReceipt());
			ps.setInt(6, t.getAuthor().getId());

			int i = 7;
			try {
				ps.setInt(i, t.getResolver().getId());
				i++;
			} catch (NullPointerException e) {
			}
			log.debug("after try block: " + i);
			ps.setInt(i, t.getStatus().id());
			log.debug("after try block: " + i);
			ps.setInt(++i, t.getType().id());
			log.debug("after try block: " + i);
			ps.setInt(++i, t.getId());
			log.debug("after try block: " + i);

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
				if (c != null || c.isClosed()) {
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
	public int delete(Reimbursement t) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM ers_schema.reimbursements WHERE id = ?";

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
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (c != null || c.isClosed()) {
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
	public List<Reimbursement> getAll() {
		List<Reimbursement> reims = new ArrayList<>();

		String sql = "SELECT r.id, r.amount, r.submitted, r.resolved, r.description, "
				+ "		r.receipt, e.id as e_id, e.username as e_username, e.password as e_password, "
				+ "		e.first_name as e_first_name, e.last_name as e_last_name, "
				+ "		e.email as e_email, e.role_id as e_role_id, m.id as m_id, "
				+ "		m.username as m_username, m.password as m_password, m.first_name as m_first_name, "
				+ "		m.last_name as m_last_name, m.email as m_email, m.role_id as m_role_id, "
				+ "		r.status, r.rtype FROM ers_schema.reimbursements r LEFT JOIN ers_schema.users e "
				+ "		ON r.author = e.id LEFT JOIN ers_schema.users m ON r.resolver = m.id;";

		try {
			c = PostgresConnection.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reimbursement reim = new Reimbursement();
				reim.setId(rs.getInt("id"));
				reim.setAmount(rs.getDouble("amount"));
				reim.setSubmitted(rs.getDate("submitted"));
				reim.setResolved(rs.getDate("resolved"));
				reim.setDescription(rs.getString("description"));
				reim.setReceipt(rs.getBytes("receipt"));

				// lets solve this through a join
				// e stands for employee
				User e = new User();
				e.setId(rs.getInt("e_id"));
				e.setUsername(rs.getString("e_username"));
				e.setFirst_name(rs.getString("e_first_name"));
				e.setLast_name(rs.getString("e_last_name"));
				e.setEmail(rs.getString("e_email"));
				e.setPassword(rs.getString("e_password"));
				e.setRole(getRole(rs.getInt("e_role_id")));

				reim.setAuthor(e);

				User m = new User();
				m.setId(rs.getInt("m_id"));
				m.setUsername(rs.getString("m_username"));
				m.setFirst_name(rs.getString("m_first_name"));
				m.setLast_name(rs.getString("m_last_name"));
				m.setEmail(rs.getString("m_email"));
				m.setPassword(rs.getString("m_password"));
				m.setRole(getRole(rs.getInt("m_role_id")));

				if (m.getEmail() == null) {
					m = null;
				}

				reim.setResolver(m);

				reim.setStatus(getStatus(rs.getInt("status")));
				reim.setType(getType(rs.getInt("rtype")));

				reims.add(reim);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reims;
	}

	private Role getRole(Integer id) {
		switch (id) {
		case 1:
			return Role.EMPLOYEE;
		case 2:
			return Role.MANAGER;
		case 3:
			return Role.FINANCE_MANAGER;
		}
		return null;
	}

	private Status getStatus(Integer id) {
		switch (id) {
		case 1:
			return Status.PENDING;
		case 2:
			return Status.APPROVED;
		case 3:
			return Status.DENIED;
		}
		return null;
	}

	private ReimType getType(Integer id) {
		switch (id) {
		case 1:
			return ReimType.LODGING;
		case 2:
			return ReimType.TRAVEL;
		case 3:
			return ReimType.FOOD;
		default:
			return ReimType.OTHER;
		}
	}
}
