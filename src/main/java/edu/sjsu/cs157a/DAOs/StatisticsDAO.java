package edu.sjsu.cs157a.DAOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
