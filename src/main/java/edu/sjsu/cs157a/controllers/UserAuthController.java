package edu.sjsu.cs157a.controllers;

import java.util.Scanner;

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
	
	public User Login() {
		Scanner scan = new Scanner(System.in);
		String emailHolder;
		String pwHolder;
		String inputHolder;
		User currentUser = null;
		while(true) {
			// keep trying to log in until user logs in or 
			// user wants to quite.
			System.out.println("Please enter your email address: ");
			emailHolder = scan.nextLine();
			System.out.println("Please enter your password: ");
			pwHolder = scan.nextLine();
			
			try {
				currentUser = userDAO.getUser(emailHolder, pwHolder);
				return currentUser;
			}catch(AuthenticationException e) {
				System.err.println(e.getMessage());
				while(true) {
					System.out.println("Would you like to try again?");
					System.out.println("[1] Yes.");
					System.out.println("[2] No.");
					inputHolder = scan.nextLine();
					if(Integer.parseInt(inputHolder) == 1) break;
					else if(Integer.parseInt(inputHolder) == 2) return currentUser;
					else System.err.println("Invalid Input.");
				}
			}
		}
	}
}
