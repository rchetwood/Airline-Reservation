package edu.sjsu.cs157a.DAOs;

import java.sql.Date;
import java.util.Set;

import javax.security.sasl.AuthenticationException;

import org.hibernate.Hibernate;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.cs157a.DAOs.UserDAO;
import edu.sjsu.cs157a.models.Flight;
import edu.sjsu.cs157a.models.User;

public class UserDAOTest extends BaseTest {
	
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void init() {
		try {
			initConnectionAndDatabase();
			userDAO = new UserDAO();
			userDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addUserTest() {
		User riley = new User("Riley", "Chetwood", "rileychetwood@gmail.com", "pass123", Date.valueOf("1993-07-02"), true);
		Integer uID = userDAO.addUser(riley);
		assert uID != null;
	}

	@Test(expected = AuthenticationException.class)
	public void getUserThatDoesNotExistTest() throws AuthenticationException {
		userDAO.getUser("JJWatt@gmail.com", "pass123");
	}
	
	@Test(expected = AuthenticationException.class)
	public void getUserThatDoesExistButWrongPasswordTest() throws AuthenticationException {
		userDAO.getUser("rileychetwood@gmail.com", "pass");
	}
	
	@Test
	public void getUserThatHasCorrectEmailAndPasswordTest() throws AuthenticationException {
		User user = userDAO.getUser("rileychetwood@gmail.com", "pass123");
		assert user.getEmail().equals("rileychetwood@gmail.com");
	}
	
	@Test(expected = AuthenticationException.class)
	public void removeUserTest() throws AuthenticationException {
		User ron = new User("Ron", "Weasley", "rw@gmail.com", "ISolemnlySwear", Date.valueOf("1980-03-01"), false);
		Integer ronUId = userDAO.addUser(ron);
		userDAO.removeUser(ronUId);
		User deletedUser = userDAO.getUser("rw@gmail.com", "ISolemnlySwear");
		assert deletedUser == null;
	}
	
	@Test
	public void addTripTest() throws AuthenticationException  {
		User riley = userDAO.getUser("rc@gmail.com", "pass123");
		int oldTrips = riley.getTrips().size();
		userDAO.addTrip(1, 100);
		riley = userDAO.getUser("rc@gmail.com", "pass123");
		int newTrips = riley.getTrips().size();
		assert oldTrips == newTrips - 1;
	}
	
	@Test
	public void getAllUsersTripsTest() throws AuthenticationException {
		User riley = userDAO.getUser("rc@gmail.com", "pass123");
		Set<Flight> trips = userDAO.getAllUsersTrips(riley.getuID());
		for(Flight f: trips) {
			assert f.getManifest().contains(riley);
		}
		
	}
	
	@Test
	public void removeTripTest() throws AuthenticationException {
		User riley = userDAO.getUser("rc@gmail.com", "pass123");
		Set<Flight> rileysTrips = riley.getTrips();
		int oldSize = rileysTrips.size();
		Flight removeThisFlight = rileysTrips.iterator().next();
		userDAO.removeTrip(riley.getuID(), removeThisFlight);
		riley = userDAO.getUser("rc@gmail.com", "pass123");
		rileysTrips = riley.getTrips();
		int newSize = rileysTrips.size();
		assert oldSize == newSize + 1;
	}
	
	@Test
	public void updateUserTest() throws AuthenticationException {
		User john = new User("John", "Smith", "jsmith@gmail.com", "pass123", Date.valueOf("1986-11-06"), false);
		Integer uID = userDAO.addUser(john);
		john = userDAO.getUser("jsmith@gmail.com", "pass123");
		// null values ignored
		userDAO.updateUser(uID, null, "Jay", null, null, null, null);
		john = userDAO.getUser("jsmith@gmail.com", "pass123");
		assert "Jay".equals(john.getLname());
	}
}
