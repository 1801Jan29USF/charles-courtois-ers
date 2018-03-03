package com.revature.service;

import com.revature.beans.Reimbursement;
import com.revature.dao.DAOLayer;

public class AddReimbursementService {
	private DAOLayer dao = new DAOLayer();
	// private Logger log = Logger.getRootLogger();

	public void addReimbursement(Reimbursement r) {
		dao.addReimbursement(r);
	}
}
