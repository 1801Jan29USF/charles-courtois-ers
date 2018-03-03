package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.User;
import com.revature.service.UserService;

public class LoginController implements HTTPController {
	private Logger log = Logger.getRootLogger();
	private UserService userService = new UserService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String url = req.getPathInfo();
		if (url.equals("/login")) {
			String json = req.getReader().lines().reduce((acc, cur) -> acc + cur).get();
			ObjectMapper om = new ObjectMapper();
			User credentials = (User) om.readValue(json, User.class);
			User u = userService.login(credentials.getUserName(), credentials.getPassWord());
			log.trace(u);

			if (u != null) {
				HttpSession sess = req.getSession();
				sess.setAttribute("user", u);
				String respJson = om.writeValueAsString(u);
				resp.getWriter().write(respJson);
			} else {
				resp.setStatus(401);
			}
		}
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	}
}