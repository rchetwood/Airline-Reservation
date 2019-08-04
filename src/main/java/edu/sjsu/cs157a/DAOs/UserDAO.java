package edu.sjsu.cs157a.DAOs;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.sasl.AuthenticationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.User;

public class UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public User getUser(String email, String password) throws AuthenticationException {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User authenticatedUser = null;

		try {
			tx = session.beginTransaction();
			if ((authenticatedUser = isUserEmail(session, email)) == null) {
				throw new AuthenticationException("The email " + email + " is not registered with our service.");
			} else if (!authenticatedUser.getPassword().equals(hashPassword(password))) {
				throw new AuthenticationException("Username or password is incorrect.");
			}
			logger.debug(authenticatedUser + " has been retrieved.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}

		return authenticatedUser;
	}
	
	public Set<Flight> getAllUsersTrips(Integer uID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set<Flight> trips = null;

		try {
			tx = session.beginTransaction();
			User user = (User)session.get(User.class, uID);
			Hibernate.initialize(user.getTrips());
			trips = user.getTrips();
			for(Flight f : trips) {
				Hibernate.initialize(f.getDeparture());
				Hibernate.initialize(f.getDestination());
				Hibernate.initialize(f.getAirline());
				Hibernate.initialize(f.getPlane());
				Hibernate.initialize(f.getManifest());
			}
			logger.debug(user.getFname() + "'s trips have been retrieved.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}

		return trips;
	}

	public Integer addUser(User newUser) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer uID = null;

		try {
			tx = session.beginTransaction();
			newUser.setPassword(hashPassword(newUser.getPassword()));
			uID = (Integer) session.save(newUser);
			logger.debug(newUser + " has been registered.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}

		return uID;
	}
	
	public void addTrip(Integer uID, Integer fID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			// get user
			User user = (User)session.get(User.class, uID);
			
			// get flight 
			Flight flight = (Flight)session.get(Flight.class, fID);
		
			// add to trip
			user.getTrips().add(flight);
			
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
	
	public void updateUser(Integer uID,String fname, String lname, String email, String password, Date birthDate, Boolean isAdmin) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, uID);
			
			if(fname!=null) user.setFname(fname);
			if(lname!=null) user.setLname(lname);
			if(email!=null) user.setEmail(email);
			if(password!=null) user.setPassword(password);
			if(birthDate!=null) user.setBirthDate(birthDate);
			if(isAdmin!=null) user.setIsAdmin(isAdmin);
			
			session.update(user);
			
			logger.debug(user + " has been deleted.");
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

	public void removeUser(Integer uID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, uID);
			session.delete(user);
			logger.debug(user + " has been deleted.");
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
	
	public void removeTrip(Integer uID, Flight f) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, uID);
			user.getTrips().remove(f);
			logger.debug(user + " has been deleted.");
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
	

	private String hashPassword(String unhashedPassword) {
		String salt1 = "$4J", salt2 = "M%a";
		return DigestUtils.sha256Hex(salt1 + unhashedPassword + salt2);
	}

	private User isUserEmail(Session session, String email) {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root).where(cb.equal(root.get("email"), email));
		Query<User> query = session.createQuery(cr);
		List<User> result = query.getResultList();
		for (User u : result) {
			if (u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}


}
