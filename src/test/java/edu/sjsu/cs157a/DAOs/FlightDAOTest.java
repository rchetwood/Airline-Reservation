package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.cs157a.models.Airport;
import edu.sjsu.cs157a.models.Flight;

public class FlightDAOTest extends BaseTest {
	
	private static FlightDAO flightDAO;
	private static AirportDAO airportDAO;
	
	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			flightDAO = new FlightDAO();
			flightDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
			airportDAO = new AirportDAO();
			airportDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
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
			System.out.println(f);
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
			System.out.println(searchResults.get(i));
			if(searchResults.get(i).getPrice() > searchResults.get(searchResults.size()-1).getPrice()) {
				fail();
			}
		}
	}

}
