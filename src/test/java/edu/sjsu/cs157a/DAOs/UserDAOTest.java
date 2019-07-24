package edu.sjsu.cs157a.DAOs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.Properties;

import javax.security.sasl.AuthenticationException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;


import edu.sjsu.cs157a.DAOs.UserDAO;
import edu.sjsu.cs157a.models.User;

public class UserDAOTest {
	
	private static UserDAO userDAO;
	private static Connection conn;
	private static Properties prop;
	
	@BeforeClass
	public static void init() {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("src/main/resources/hibernate.properties"));
			conn = DriverManager.getConnection(prop.getProperty("hibernate.connection.url"),
											   prop.getProperty("hibernate.connection.username"),
											   prop.getProperty("hibernate.connection.password"));
			executeSQLScript(conn, "reservation.sql");
			
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
	
	public static void executeSQLScript(Connection conn, String fileName) throws Exception {
		final String basePath = new File("").getAbsolutePath();
		final String projectPath = prop.getProperty("projectPath");
		final String aSQLScriptFilePath = basePath + projectPath + fileName;
		ScriptRunner sr = null;

		try {
			sr = new ScriptRunner(conn);
			Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
			sr.runScript(reader);
		} catch (Exception e) {
			throw e;
		}
	}
}
