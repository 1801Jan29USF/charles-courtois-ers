package com.revature.service;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.dao.DAOLayer;

public class UserService {
	private DAOLayer dao = new DAOLayer();
	private Logger log = Logger.getRootLogger();

	public User login(String username, String password) {
		if (username.equals(dao.getUserByUsername(username).getUserName())
				&& password.equals(dao.getUserByUsername(username).getPassWord())) {
			log.trace(dao.getUserByUsername(username));
			return dao.getUserByUsername(username);
		}
		return null;
	}
}
