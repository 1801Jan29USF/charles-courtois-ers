package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class DAOLayer implements DAOInterface {
	private Logger log = Logger.getRootLogger();
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	public static void main(String[] args) {
		// DAOLayer dao = new DAOLayer();
		// Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// User u = User.getUser(1, 1, "beasd", "pass", "Charles", "Courtois", "oh
		// asdah");
		// dao.addUser(u);
		// Reimbursement r = Reimbursement.getReimbursement(1, 10001, timestamp, 1, 3,
		// 1, 1);
		// dao.addReimbursement(r);
		// u.setUserName("beasd");
		// u.setEmail("oh asdah");
		// dao.updateUser(u);
		// r.setAmount(1235125);
		// r.setAuthorId(3);
		// r.setDateResolved(timestamp);
		// dao.updateReimbursement(r);
		// System.out.println(dao.getUser(1).toString());
		// System.out.println(dao.getReimbursement(1).toString());
		// System.out.println(dao.getReimbursements().toString());
	}

	@Override
	public void addUser(User u) {
		log.trace("Method called to insert new user.");
		log.trace("Attempting to get connection to db.");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO ers_users(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)"
							+ "    VALUES(?,?,?,?,?,?)");
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassWord());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getRoleId());

			int rowsInserted = ps.executeUpdate();
			log.debug("Query inserted " + rowsInserted + " rows into the db.");

		} catch (SQLException e) {
			log.warn("Failed to insert new user.");
		}
	}

	@Override
	public void addReimbursement(Reimbursement r) {
		log.trace("Method called to insert new reimbursement.");
		log.trace("Attempting to get connection to db.");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)"
							+ "    VALUES(?,?,?,?,?,?,?)");
			ps.setDouble(1, r.getAmount());
			ps.setTimestamp(2, r.getDateSubmitted());
			ps.setString(3, r.getDescription());
			ps.setInt(4, r.getAuthorId());
			ps.setInt(5, r.getResolverId());
			ps.setInt(6, r.getStatusId());
			ps.setInt(7, r.getTypeId());

			int rowsInserted = ps.executeUpdate();
			log.debug("Query inserted " + rowsInserted + " rows into the db.");

		} catch (SQLException e) {
			log.warn("Failed to insert new reimbursement.");
		}
	}

	@Override
	public User getUser(int id) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE ers_users_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = User.getUser(rs.getInt("ers_users_id"), rs.getInt("user_role_id"),
						rs.getString("ers_password"), rs.getString("user_first_name"), rs.getString("user_last_name"),
						rs.getString("user_email"), rs.getString("ers_username"));
				return u;
			} else {
				log.trace("No user found with id " + id);
			}

		} catch (SQLException e) {
			log.warn("Failed to query user.");
		}
		return null;
	}

	@Override
	public Reimbursement getReimbursement(int id) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"), rs.getBlob("reimb_receipt"), rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				return r;
			} else {
				log.trace("No reimbursement found with id " + id);
			}

		} catch (SQLException e) {
			log.warn("Failed to query user.");
		}
		return null;
	}

	@Override
	public void updateUser(User u) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ers_users SET ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ? WHERE ers_users_id = ?");
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassWord());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getId());

			int rowsInserted = ps.executeUpdate();
			log.debug("Query updated " + rowsInserted + " row in the db.");

		} catch (SQLException e) {
			log.warn("Failed to update user.");
		}
	}

	@Override
	public void updateReimbursement(Reimbursement r) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ers_reimbursement SET reimb_amount = ?, reimb_submitted = ?, reimb_resolved = ?, reimb_description = ?,"
							+ "reimb_receipt = ?, reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? WHERE reimb_id = ?");
			ps.setDouble(1, r.getAmount());
			ps.setTimestamp(2, r.getDateSubmitted());
			ps.setTimestamp(3, r.getDateResolved());
			ps.setString(4, r.getDescription());
			ps.setBlob(5, r.getReceipt());
			ps.setInt(6, r.getAuthorId());
			ps.setInt(7, r.getResolverId());
			ps.setInt(8, r.getStatusId());
			ps.setInt(9, r.getTypeId());
			ps.setInt(10, r.getId());

			int rowsInserted = ps.executeUpdate();
			log.debug("Query updated " + rowsInserted + " row in the db.");

		} catch (SQLException e) {
			log.warn("Failed to update user.");
		}
	}

	@Override
	public User getUserByUsername(String username) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE ers_username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = User.getUser(rs.getInt("ers_users_id"), rs.getInt("user_role_id"),
						rs.getString("ers_username"), rs.getString("ers_password"), rs.getString("user_first_name"),
						rs.getString("user_last_name"), rs.getString("user_email"));
				return u;
			} else {
				log.trace("No user found with username " + username);
			}

		} catch (SQLException e) {
			log.warn("Failed to query user.");
		}
		return null;
	}

	@Override
	public List<Reimbursement> getReimbursements(int id) {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_author = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> list = new ArrayList<Reimbursement>();
			while (rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"), rs.getBlob("reimb_receipt"), rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				list.add(r);
			}
			return list;

		} catch (SQLException e) {
			log.warn("Failed to query user.");
		}
		return null;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() {
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement");
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> list = new ArrayList<Reimbursement>();
			while (rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"), rs.getBlob("reimb_receipt"), rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				list.add(r);
			}
			return list;

		} catch (SQLException e) {
			log.warn("Failed to query user.");
		}
		return null;
	}

	@Override
	public void approveordeny(Reimbursement r, int managerId) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ers_reimbursement SET reimb_resolved = ?, reimb_resolver = ? , reimb_status_id = ? WHERE reimb_id = ?");

			ps.setTimestamp(1, timestamp);
			ps.setInt(2, managerId);
			ps.setInt(3, r.getStatusId());
			ps.setInt(4, r.getId());

			int rowsInserted = ps.executeUpdate();
			log.debug("Query updated " + rowsInserted + " row in the db.");

		} catch (SQLException e) {
			log.warn("Failed to update user.");
		}
	}
}