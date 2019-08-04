package edu.sjsu.cs157a.controllers;

import java.sql.Date;
import java.util.Iterator;
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
		System.out.print("Destination airport: ");
		String destinationAirport = in.nextLine();
		System.out.print("Departure date: ");
		String departingDate = in.nextLine();
		
		
		Airport a1 = airportDAO.getAirport(departingAirport);
		Airport a2 = airportDAO.getAirport(destinationAirport);
		
		System.out.println("Would you want you result to be sorted?");
		System.out.println("[1] Yes");
		System.out.println("[2] No");
		String temp = in.nextLine();
		while(true) {
			if(Integer.parseInt(temp) == 1) {
				List<Flight> searchResults = flightDAO.search(a1, a2, Date.valueOf(departingDate), true);
				System.out.println("========================================");
				Iterator<Flight> it = searchResults.iterator();
			    while(it.hasNext()){
			        System.out.println(it.next());
			    }
				break;
			}
			else if (Integer.parseInt(temp) == 2){
				List<Flight> searchResults = flightDAO.search(a1, a2, Date.valueOf(departingDate), false);
				System.out.println("========================================");
				Iterator<Flight> it = searchResults.iterator();
			    while(it.hasNext()){
			        System.out.println(it.next());
			    }
				break;
			}
			else {
				System.out.println("Invalid Input");
				System.out.println("Would you want you result to be sorted?");
				System.out.println("[1] Yes");
				System.out.println("[2] No");
				temp = in.nextLine();
			}
		}
	
	}
	
	public List<Flight> pickFlights(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Departing airport: ");
		String departingAirport = scan.nextLine();
		System.out.print("\nDestination airport: ");
		String destinationAirport = scan.nextLine();
		System.out.print("\nDeparture date: ");
		String departingDate = scan.nextLine();
		
		
		Airport a1 = airportDAO.getAirport(departingAirport);
		Airport a2 = airportDAO.getAirport(destinationAirport);

		List<Flight> searchResults = flightDAO.search(a1, a2, Date.valueOf(departingDate), true);
		return searchResults;
	}
}
