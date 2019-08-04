package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.cs157a.models.Plane;
import edu.sjsu.cs157a.models.User;

public class StatisticsDAOTest extends BaseTest {

	private static StatisticsDAO statDAO;
	private static UserDAO userDAO;

	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
			statDAO = new StatisticsDAO();
			statDAO.setSessionFactory(sf);
			userDAO = new UserDAO();
			userDAO.setSessionFactory(sf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void popularDeparturesTest() {
		System.out.println("popularDeparturesTest");
		HashMap<String, Integer> popDepart = (HashMap<String, Integer>) statDAO.popularDepartures();
		for(String depart : popDepart.keySet()) {
			System.out.println(depart + " " + popDepart.get(depart));
		}
		System.out.println();
		assert popDepart.get("LaGrange Callaway Airport") == 2;
	}
	
	@Test
	public void popularDestinationsTest() {
		System.out.println("popularDestinationsTest");
		HashMap<String, Integer> popDest = (HashMap<String, Integer>) statDAO.popularDestinations();
		for(String dest : popDest.keySet()) {
			System.out.println(dest + " " + popDest.get(dest));
		}
		System.out.println();
		assert popDest.get("Indianapolis International Airport") == 2;
	}
	
	@Test
	public void popularAirlineTest() {
		System.out.println("popularAirlineTest");
		HashMap<String, Integer> popAirline = (HashMap<String, Integer>) statDAO.popularAirlines();
		for(String airline : popAirline.keySet()) {
			System.out.println(airline + " " + popAirline.get(airline));
		}
		System.out.println();
		assert popAirline.get("Spirit Airlines") == 3;
	}
	
	@Test 
	public void usersWithNoTripsTest() {
		System.out.println("usersWithNoTripsTest");
		User james = new User("James", "Doe", "jd@gmail.com", "pass123", Date.valueOf("1902-01-20"), false);
		userDAO.addUser(james);
		List<User> triplessUsers = statDAO.usersWithNoTrips();
		for(User u : triplessUsers) { 
			System.out.println(u);
		}
		System.out.println();
		assert triplessUsers.contains(james);
	}
	
	@Test
	public void airlinesWithMoreThanTest() {
		System.out.println("airlinesWithMoreThanTest");
		HashMap<String, Integer> airlinesHavingMore = (HashMap<String, Integer>) statDAO.airlinesWithMoreThan(8);
		for(String airline : airlinesHavingMore.keySet()) {
			System.out.println(airline + " " + airlinesHavingMore.get(airline));
		}
		System.out.println();
		assert airlinesHavingMore.size() == 7;
	}
	
	@Test
	public void  planesWithGreaterCapacityThanAverageTest() {
		System.out.println("planesWithGreaterCapacityThanAverageTest");
		List<Plane> planes = statDAO.planesWithGreaterCapacityThanAverage();
		for(Plane p : planes) { 
			System.out.println(p);
		}
		System.out.println();
		assert planes.size() == 5;
	}

}
