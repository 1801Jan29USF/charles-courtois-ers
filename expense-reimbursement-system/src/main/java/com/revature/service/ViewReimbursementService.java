package com.revature.service;

import java.sql.Timestamp;
import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.dao.DAOLayer;

public class ViewReimbursementService {
	private DAOLayer dao = new DAOLayer();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	// private Logger log = Logger.getRootLogger();

	public List<Reimbursement> getReimbursements(int id) {
		return dao.getReimbursements(id);
	}
	
	public List<Reimbursement> getAllReimbursements() {
		return dao.getAllReimbursements();
	}

	public Reimbursement approveordeny(Reimbursement r, int statusId, int managerId) {
		r.setDateResolved(timestamp);
		r.setStatusId(statusId);
		r.setResolverId(managerId);
		dao.approveordeny(r, managerId);
		return r;
	}
}
