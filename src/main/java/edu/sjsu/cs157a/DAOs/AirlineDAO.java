package edu.sjsu.cs157a.DAOs;

import java.util.Set;

import javax.security.sasl.AuthenticationException;

import org.hibernate.HibernateException;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.models.Airline;
import edu.sjsu.cs157a.models.Plane;

public class AirlineDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(AirlineDAO.class);
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Integer addAirline(Airline newAirline) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer alID = null;
		
		try {
			tx = session.beginTransaction();
			alID = (Integer) session.save(newAirline);
			logger.info(newAirline + " has been added with alID of " + alID + ".");
			tx.commit();
		} catch(HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return alID;
	}
	
	public Airline getAirline(String companyName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Airline airline = null;
		
		try {
			tx = session.beginTransaction();
			NaturalIdLoadAccess<Airline> naturalIdentifier = session.byNaturalId(Airline.class);
			naturalIdentifier.using("companyName", companyName);
			airline = (Airline)naturalIdentifier.load();
			logger.info(airline + " has been retrieved.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return airline;
	}
	

}
