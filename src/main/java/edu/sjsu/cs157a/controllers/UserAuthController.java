package edu.sjsu.cs157a.controllers;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.security.sasl.AuthenticationException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.UserDAO;
import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.Plane;
import edu.sjsu.cs157a.models.User;

public class UserAuthController {
	private static UserDAO userDAO;
	public static Scanner scan = new Scanner(System.in);
	public UserAuthController() {
		SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
		userDAO = new UserDAO();
		userDAO.setSessionFactory(sf);
	}
	
	public User Login() {
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
					else if(Integer.parseInt(inputHolder) == 2) {
						return currentUser;
					}
					else System.err.println("Invalid Input.");
				}
			}
		}
	}
	
	public int Register() {
		String inputHolder;
		User newUI = new User();
		int newUID;
		System.out.println("Please enter your first name: ");
		inputHolder = scan.nextLine();
		newUI.setFname(inputHolder);
		System.out.println("Please enter your last name: ");
		inputHolder = scan.nextLine();
		newUI.setLname(inputHolder);
		System.out.println("Please enter your email Address: ");
		inputHolder = scan.nextLine();
		newUI.setEmail(inputHolder);
		System.out.println("Please enter your password: ");
		inputHolder = scan.nextLine();
		newUI.setPassword(inputHolder);
		System.out.println("Please enter your birth date: (yyyy-mm-dd) ");
		inputHolder = scan.nextLine();
		newUI.setBirthDate(Date.valueOf(inputHolder));
		System.out.println("Please enter your age: ");
		inputHolder = scan.nextLine();
		newUI.setAge(Integer.parseInt(inputHolder));
		while(true) {
			System.out.println("Please enter your admin status: ");
			System.out.println("[1] Yes");
			System.out.println("[2] No");
			inputHolder = scan.nextLine();
			if(Integer.parseInt(inputHolder) == 1) {
				newUI.setIsAdmin(true);
				break;
			}
			else if(Integer.parseInt(inputHolder) == 2) {
				newUI.setIsAdmin(false);
				break;
			}
			else System.err.println("Invalid Input, try again.");
		}
		newUID = userDAO.addUser(newUI);
		return newUID;
	}
	
	public void showTrips(User currentUser){	
		Set<Flight> tripListResults = userDAO.getAllUsersTrips(currentUser.getuID());
		System.out.println(tripListResults);
	}
	
	public void removeUser(User currentUser) {
		userDAO.removeUser(currentUser.getuID());
	}
	
	public void addNewTrip(User currentUser) {
		boolean happyResult = false;
		List<Flight> results = null;
		//while(!happyResult) {
			SearchController sc = new SearchController();
			results = sc.pickFlights();
			for(int i = 1; i < results.size(); i++) {
				System.out.println("[" + i + "] " + results.get(i));
			}

		System.out.println("Enter the flight you want to pick: ");
		if(scan.hasNextLine()) {
			String tempHolder = scan.nextLine();
			userDAO.addTrip(currentUser.getuID(), results.get(Integer.parseInt(tempHolder)).getfID());
		}
	}
}