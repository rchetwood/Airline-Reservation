package edu.sjsu.cs157a.DAOs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.Plane;
import edu.sjsu.cs157a.models.User;

public class StatisticsDAO {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Map<String, Integer> popularAirlines() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String, Integer> popularAirlines = new HashMap<>();

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("select companyName, count(companyName) "
					+ "from trip natural join flight " + "natural join fleet natural join airline "
					+ "group by companyName order by count(companyName) desc");
			List<Object[]> result = q.list();
			for (Object[] row : result) {
				String coName = row[0].toString();
				int num = Integer.parseInt(row[1].toString());
				popularAirlines.put(coName, num);
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

		return popularAirlines;
	}

	public Map<String, Integer> popularDepartures() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String, Integer> popularDepartures = new HashMap<>();

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("SELECT name, count(name) " + "FROM trip NATURAL JOIN flight "
					+ "NATURAL JOIN fleet INNER JOIN airport " + "ON departure=apID " + "GROUP BY name "
					+ "ORDER BY COUNT(name) DESC");
			List<Object[]> result = q.list();
			for (Object[] row : result) {
				String coName = row[0].toString();
				int num = Integer.parseInt(row[1].toString());
				popularDepartures.put(coName, num);
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

		return popularDepartures;
	}

	public Map<String, Integer> popularDestinations() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String, Integer> popularDepartures = new HashMap<>();

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("SELECT name, count(name) " + "FROM trip NATURAL JOIN flight "
					+ "NATURAL JOIN fleet INNER JOIN airport " + "ON destination=apID " + "GROUP BY name "
					+ "ORDER BY COUNT(name) DESC");
			List<Object[]> result = q.list();
			for (Object[] row : result) {
				String coName = row[0].toString();
				int num = Integer.parseInt(row[1].toString());
				popularDepartures.put(coName, num);
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

		return popularDepartures;
	}

	public List<User> usersWithNoTrips() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<User> triplessUsers = new ArrayList<User>();

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("select user.uID " + "FROM user left outer join trip "
					+ "on user.uID=trip.uID " + "where fID is null;");
			List<Object> result = q.list();
			for (Object row : result) {
				Integer uID = (Integer) row;
				User user = (User) session.get(User.class, uID);
				logger.debug(user + " retrieved.");
				triplessUsers.add(user);
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

		return triplessUsers;
	}
	
	public Map<String, Integer> airlinesWithMoreThan(Integer moreThanThis) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String, Integer> airlinesHavingMore = new HashMap<>();

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("select companyName, count(pID) "
					+ "from airline natural join fleet "
					+ "natural join plane "
					+ "group by companyName "
					+ "having count(pID) > :moreThanThis "
					+ "order by count(pID) desc;").setParameter("moreThanThis", moreThanThis);
			List<Object[]> result = q.list();
			for (Object[] row : result) {
				String coName = row[0].toString();
				int num = Integer.parseInt(row[1].toString());
				airlinesHavingMore.put(coName, num);
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

		return airlinesHavingMore;
	}
	
	public List<Plane> planesWithGreaterCapacityThanAverage() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Plane> planesAboveAverageCapacity = new ArrayList<Plane>();;

		try {
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("select p1.pID "
					+ "from plane p1 "
					+ "where capacity > (select avg(capacity) from plane p2 where p2.manufacturer=p1.manufacturer);");
			List<Object> result = q.list();
			for (Object row : result) {
				Integer pID = (Integer) row;
				Plane plane = (Plane) session.get(Plane.class, pID);
				logger.debug(plane + " retrieved.");
				planesAboveAverageCapacity.add(plane);
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

		return planesAboveAverageCapacity;
	}

}
