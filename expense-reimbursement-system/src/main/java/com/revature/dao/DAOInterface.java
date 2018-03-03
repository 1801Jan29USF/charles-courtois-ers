package com.revature.dao;

import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public interface DAOInterface {
	void addUser(User u);

	void addReimbursement(Reimbursement r);

	void updateUser(User u);

	void updateReimbursement(Reimbursement r);

	void approveordeny(Reimbursement r, int managerId);

	User getUser(int id);

	User getUserByUsername(String username);

	Reimbursement getReimbursement(int id);

	List<Reimbursement> getReimbursements(int id);

	public List<Reimbursement> getAllReimbursements();
}