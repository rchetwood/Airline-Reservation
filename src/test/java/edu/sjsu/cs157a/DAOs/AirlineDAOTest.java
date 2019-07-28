package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.HashSet;

import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.cs157a.models.Airline;
import edu.sjsu.cs157a.models.Plane;

public class AirlineDAOTest extends BaseTest {
	
	private static AirlineDAO airlineDAO;
	
	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			airlineDAO = new AirlineDAO();
			airlineDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void addAirlineTest() {
		Integer alID = airlineDAO.addAirline(getTestAirline());
		assert alID != null;
	}
	
	@Test
	public void getAirlineTest() {
		Airline airline = airlineDAO.getAirline("Delta Airlines");
		assert airline.getCompanyName().equals("Delta Airlines");
	}
	
	private Airline getTestAirline() {
		Plane p1 = new Plane("Boeing", "777", 400);
		Plane p2 = new Plane("Boeing", "777", 400);
		Plane p3 = new Plane("Boeing", "777", 400);
		Plane p4 = new Plane("Airbus", "A220", 141);
		
		HashSet<Plane> fleet = new HashSet<>();
		fleet.add(p1);
		fleet.add(p2);
		fleet.add(p3);
		fleet.add(p4);
		
		return new Airline("Riley's Airline", "San Jose", Date.valueOf("1993-07-02"), "RTC", fleet);
	}

}
