package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.service.AddReimbursementService;

public class AddReimbursementController implements HTTPController {
	// private Logger log = Logger.getRootLogger();
	private AddReimbursementService addService = new AddReimbursementService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User userCookie = (User) req.getSession().getAttribute("user");
		if (req.getSession().getAttribute("user") == null) {
			resp.setStatus(403);
			return;
		}

		String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
		ObjectMapper om = new ObjectMapper();
		Reimbursement reimb = (Reimbursement) om.readValue(json, Reimbursement.class);
		reimb.setAuthorId(userCookie.getId());
		addService.addReimbursement(reimb);

		if (reimb != null) {
			HttpSession sess = req.getSession();
			sess.setAttribute("reimbursement", reimb);
			String respJson = om.writeValueAsString(reimb);
			resp.getWriter().write(respJson);
		}
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}
}