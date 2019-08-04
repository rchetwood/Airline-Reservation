package edu.sjsu.cs157a.controllers;

import java.util.Scanner;

import javax.security.sasl.AuthenticationException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.UserDAO;
import edu.sjsu.cs157a.models.User;

public class LoginController {

	private UserDAO userDAO;
	
	public LoginController() {
		SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
		userDAO = new UserDAO();
		userDAO.setSessionFactory(sf);
	}
	
	public User login() {
		Scanner in = new Scanner(System.in);
		User authenticatedUser = null;
		
		
		while(true) {
			System.out.print("Email: ");
			String email = in.nextLine();
			
			System.out.print("Passwrod: ");
			String password = in.nextLine();
			
			try {
				authenticatedUser = userDAO.getUser(email, password);
				return authenticatedUser;
			} catch(AuthenticationException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
}
