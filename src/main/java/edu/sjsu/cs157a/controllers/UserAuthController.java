package edu.sjsu.cs157a.controllers;

import javax.security.sasl.AuthenticationException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.UserDAO;
import edu.sjsu.cs157a.models.User;

public class UserAuthController {
	private static UserDAO userDAO;
	
	public UserAuthController() {
		SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
		userDAO = new UserDAO();
		userDAO.setSessionFactory(sf);
	}
	
	public boolean run(User tempUI) throws AuthenticationException {
		if(tempUI.equals(userDAO.getUser(tempUI.getEmail(), tempUI.getPassword()))) return true;
		else return false;
	}
}
