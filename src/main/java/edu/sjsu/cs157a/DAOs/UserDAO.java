package edu.sjsu.cs157a.DAOs;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.sasl.AuthenticationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.sjsu.cs157a.models.User;

public class UserDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Integer addUser(User newUser) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer uID = null;
		
		try {
			tx = session.beginTransaction();
			newUser.setPassword(hashPassword(newUser.getPassword()));
			uID = (Integer) session.save(newUser);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return uID;
	}
	
	public User getUser(String email, String password) throws AuthenticationException { 
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User authenticatedUser = null;
		
		try {
			tx = session.beginTransaction();
			
			if(!isUserEmail(session, email)) {
				throw new AuthenticationException("The email " + email + " is not registered with our service.");
			}
			
			 
			 
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		}
		
		return null;
	}
	
	private String hashPassword(String unhashedPassword) {
		String salt1 =  "$4J", salt2 = "M%a";
		return DigestUtils.sha256Hex(salt1 + unhashedPassword + salt2);
	}
	
	private boolean isUserEmail(Session session, String email) {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root).where(cb.equal(root.get("email"), email));
		Query<User> query = session.createQuery(cr);
		List<User> result = query.getResultList();
		for(User u : result) {
			System.out.println("User: " + u);
			if(u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
}
	

