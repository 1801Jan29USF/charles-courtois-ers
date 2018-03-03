package com.revature.front.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.revature.controllers.AddReimbursementController;
import com.revature.controllers.LoginController;
import com.revature.controllers.ViewReimbursementController;

public class DispatcherServlet extends DefaultServlet {
	private static final long serialVersionUID = -5084529041178742947L;
	private Logger log = Logger.getRootLogger();
	private LoginController lc = new LoginController();
	private ViewReimbursementController vrc = new ViewReimbursementController();
	private AddReimbursementController arc = new AddReimbursementController();

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		super.service(arg0, arg1);
		arg1.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		arg1.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		arg1.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		arg1.addHeader("Access-Control-Allow-Credentials", "true");
		arg1.setContentType("application/json");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		log.trace("GET request made with path: " + url);
		if (url.startsWith("/static/")) {
			super.doGet(request, response);
			return;
		} else if (url.startsWith("/login")) {
			lc.doGet(request, response);
		} else if (url.startsWith("/reimbursements/1")) {
			vrc.doGet(request, response);
		} else if (url.startsWith("/reimbursements/2")) {
			arc.doGet(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		log.trace("POST request made with path: " + url);
		if (url.startsWith("/login")) {
			lc.doPost(request, response);
		} else if (url.startsWith("/reimbursements/1")) {
			vrc.doPost(request, response);
		} else if (url.startsWith("/reimbursements/2")) {
			arc.doPost(request, response);
		}
	}

	@Override
	protected void doPut(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String url = arg0.getPathInfo();
		log.trace("PUT request made with path: " + url);
		if (url.startsWith("/login")) {
			lc.doPut(arg0, arg1);
		} else if (url.startsWith("/reimbursements/1")) {
			vrc.doPut(arg0, arg1);
		} else if (url.startsWith("/reimbursements/2")) {
			arc.doPut(arg0, arg1);
		}
	}
}
