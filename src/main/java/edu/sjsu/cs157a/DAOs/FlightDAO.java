package edu.sjsu.cs157a.DAOs;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.Exceptions.FlightException;
import edu.sjsu.cs157a.models.Airport;
import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.User;

public class FlightDAO {
	private static final Logger logger = LoggerFactory.getLogger(FlightDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Integer addFlight(Flight newFlight) {

		return null;
	}

	public Flight getFlight(Integer fID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Flight flight = null;

		try {
			tx = session.beginTransaction();
			flight = (Flight) session.get(Flight.class, fID);
			Hibernate.initialize(flight.getDeparture());
			Hibernate.initialize(flight.getDestination());
			Hibernate.initialize(flight.getAirline());
			Hibernate.initialize(flight.getManifest());
			logger.debug(flight + " has been retrieved.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}

		return flight;
	}

	public List<Flight> search(Airport departure, Airport destination, Date departureDate, boolean sortByPrice) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Flight> flights = Collections.emptyList();

		try {
			tx = session.beginTransaction();
			logger.debug("Searhing with the following criteria...." + departure.getCity() + " " + destination.getCity() + " " +
						departureDate + " " + sortByPrice);

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Flight> cr = cb.createQuery(Flight.class);
			Root<Flight> flightTable = cr.from(Flight.class);
			
			Predicate p1 = cb.and(cb.equal(flightTable.get("departure"), departure));
			Predicate p2 = cb.and(cb.equal(flightTable.get("destination"), destination));
			Predicate p3 = cb.and(cb.equal(flightTable.get("departureDate"), departureDate));
			Predicate p4 = cb.and(cb.gt(flightTable.get("seatsAvailable"), 0));
			
			Predicate[] p = {p1,p2,p3,p4};
			cr.select(flightTable).where(cb.and(p));
			
			flights = session.createQuery(cr).list();
			for(Flight f : flights) { 
				logger.debug(f + " matches criteria.");
				Hibernate.initialize(f.getDeparture());
				Hibernate.initialize(f.getDestination());
				Hibernate.initialize(f.getAirline());
				Hibernate.initialize(f.getPlane());
				Hibernate.initialize(f.getManifest());
			}
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}

		
		if(sortByPrice) {
			Comparator<Flight> priceComp = new Comparator<Flight>() {
				public int compare(Flight f1, Flight f2) {
					return f1.getPrice().compareTo(f2.getPrice());
				}
			};
			flights.sort(priceComp);
		}
		
		return flights;
	}
	
	public void addUserToFlight(Integer fID, User user) throws FlightException {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Flight flight = null;

		try {
			tx = session.beginTransaction();
			flight = (Flight) session.get(Flight.class, fID);
			if(!(flight.getSeatsAvailable() > 0)) {
				throw new FlightException("No seats available.");
			}
			flight.getManifest().add(user);
			flight.setSeatsAvailable(flight.getSeatsAvailable()-1);
			logger.debug(user + " has been added to " + flight + ".");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}
