package edu.sjsu.cs157a.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.AirportDAO;
import edu.sjsu.cs157a.DAOs.FlightDAO;
import edu.sjsu.cs157a.models.Airport;
import edu.sjsu.cs157a.models.Flight;

public class SearchController {
	private static FlightDAO flightDAO;
	private static AirportDAO airportDAO;
	
	public SearchController() {
		SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
		flightDAO = new FlightDAO();
		flightDAO.setSessionFactory(sf);
		airportDAO = new AirportDAO();
		airportDAO.setSessionFactory(sf);
	}
	
	public void run() {
		Scanner in = new Scanner(System.in);
		System.out.print("Departing airport: ");
		String departingAirport = in.nextLine();
		System.out.print("\nDestination airport: ");
		String destinationAirport = in.nextLine();
		System.out.print("\nDeparture date: ");
		String departingDate = in.nextLine();
		
		Airport a1 = airportDAO.getAirport(departingAirport);
		Airport a2 = airportDAO.getAirport(destinationAirport);
		
		List<Flight> searchResults = flightDAO.search(a1, a2, Date.valueOf(departingDate), true);
		
		System.out.println("========================================");
		System.out.println(searchResults);
		in.close();
	
	}
}
