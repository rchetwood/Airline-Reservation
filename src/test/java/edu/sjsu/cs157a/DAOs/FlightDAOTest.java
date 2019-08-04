package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.security.sasl.AuthenticationException;

import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.cs157a.models.Airport;
import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.User;

public class FlightDAOTest extends BaseTest {
	
	private static FlightDAO flightDAO;
	private static AirportDAO airportDAO;
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			flightDAO = new FlightDAO();
			flightDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
			airportDAO = new AirportDAO();
			airportDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
			userDAO = new UserDAO();
			userDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFlightTest() {
		Flight flight = flightDAO.getFlight(100);
		assert flight.getfID() == 100;
	}

	@Test
	public void searchTest() {
		Airport departure = airportDAO.getAirport("Hollister Municipal Airport");
		Airport destination = airportDAO.getAirport("Albert Whitted Airport");
		ArrayList<Flight> searchResults = (ArrayList<Flight>) flightDAO.search(departure, destination, Date.valueOf("2019-08-15"), false);
		for(Flight f : searchResults) {
			assert f.getDeparture().getName().equals("Hollister Municipal Airport") &&
			   	   f.getDestination().getName().equals("Albert Whitted Airport") &&
			   	   f.getDepartureDate().equals( Date.valueOf("2019-08-15"));
		}
	}
	
	@Test
	public void searchSortByPriceTest() {
		Airport departure = airportDAO.getAirport("Hollister Municipal Airport");
		Airport destination = airportDAO.getAirport("Albert Whitted Airport");
		ArrayList<Flight> searchResults = (ArrayList<Flight>) flightDAO.search(departure, destination, Date.valueOf("2019-08-15"), true);
		
		for(int i = searchResults.size()-1; i >= 0; i--) {
			if(searchResults.get(i).getPrice() > searchResults.get(searchResults.size()-1).getPrice()) {
				fail();
			}
		}
	}
	
	@Test
	public void addUserToFlightTest() throws AuthenticationException {
		User user = userDAO.getUser("rc@gmail.com", "pass123");
		Airport departure = airportDAO.getAirport("Hollister Municipal Airport");
		Airport destination = airportDAO.getAirport("Albert Whitted Airport");
		ArrayList<Flight> searchResults = (ArrayList<Flight>) flightDAO.search(departure, destination, Date.valueOf("2019-08-15"), false);
		Flight flight  = flightDAO.getFlight(searchResults.get(0).getfID());
		Set<User> oldManifest = flight.getManifest();
		flightDAO.addUserToFlight(flight.getfID(), user);
		flight  = flightDAO.getFlight(searchResults.get(0).getfID());
		Set<User> newManifest = flight.getManifest();
		assert !oldManifest.contains(user) && newManifest.contains(user);
	}
	

}
