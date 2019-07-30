package edu.sjsu.cs157a.DAOs;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.models.Airport;

public class AirportDAO {

	private static final Logger logger = LoggerFactory.getLogger(AirportDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Integer addAirport(Airport newAirport) {

		return null;
	}

	public Airport getAirport(String name) {

		return null;
	}

	public Airport getAirport(Integer apID) {

		return null;
	}
	
	public void updateAirport() {
		
	}
	
	public void removeAirport() {
		
	}
}
