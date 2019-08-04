package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

public class StatisticsDAOTest extends BaseTest {

	private static StatisticsDAO statDAO;

	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			statDAO = new StatisticsDAO();
			statDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void popularDeparturesTest() {
		HashMap<String, Integer> popDepart = (HashMap<String, Integer>) statDAO.popularDepartures();
		for(String depart : popDepart.keySet()) {
			System.out.println(depart + " " + popDepart.get(depart));
		}
		assert popDepart.get("LaGrange Callaway Airport") == 2;
	}
	
	@Test
	public void popularDestinationsTest() {
		HashMap<String, Integer> popDest = (HashMap<String, Integer>) statDAO.popularDestinations();
		for(String dest : popDest.keySet()) {
			System.out.println(dest + " " + popDest.get(dest));
		}
		assert popDest.get("Indianapolis International Airport") == 2;
	}
	
	@Test
	public void popularAirlineTest() {
		HashMap<String, Integer> popAirline = (HashMap<String, Integer>) statDAO.popularAirlines();
		for(String airline : popAirline.keySet()) {
			System.out.println(airline + " " + popAirline.get(airline));
		}
		assert popAirline.get("Spirit Airlines") == 3;
	}

}
