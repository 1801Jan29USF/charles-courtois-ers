package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.service.ViewReimbursementService;

public class ViewReimbursementController implements HTTPController {
	// private Logger log = Logger.getRootLogger();
	private ViewReimbursementService viewService = new ViewReimbursementService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User userCookie = (User) req.getSession().getAttribute("user");
		if (req.getSession().getAttribute("user") == null) {
			resp.setStatus(403);
			return;
		}

		List<Reimbursement> reimbursements = new ArrayList<>();

		if (userCookie.getRoleId() == 2) {
			reimbursements = viewService.getAllReimbursements();
		} else {
			reimbursements = viewService.getReimbursements(userCookie.getId());
		}

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(reimbursements);
		resp.getWriter().write(json);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User userCookie = (User) req.getSession().getAttribute("user");
		String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
		ObjectMapper om = new ObjectMapper();
		Reimbursement reimb = (Reimbursement) om.readValue(json, Reimbursement.class);
		viewService.approveordeny(reimb, reimb.getStatusId(), userCookie.getId());

		HttpSession sess = req.getSession();
		sess.setAttribute("reimbursement", reimb);
		String respJson = om.writeValueAsString(reimb);
		resp.getWriter().write(respJson);
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}
}