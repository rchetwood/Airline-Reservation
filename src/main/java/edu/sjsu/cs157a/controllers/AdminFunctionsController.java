package edu.sjsu.cs157a.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.StatisticsDAO;
import edu.sjsu.cs157a.models.Plane;
import edu.sjsu.cs157a.models.User;

public class AdminFunctionsController {
	private static StatisticsDAO statDAO;
	
	public AdminFunctionsController() {
		SessionFactory sf = new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory();
		statDAO = new StatisticsDAO();
		statDAO.setSessionFactory(sf);
	}
	
	public void getPopAirlines() {
		Map<String, Integer> results = statDAO.popularAirlines();
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> iter = results.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append("----");
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
	}
	
	public void getPopDepartures() {
		Map<String, Integer> results = statDAO.popularDepartures();
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> iter = results.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append("----");
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
	}
	
	public void getPopDestination() {
		Map<String, Integer> results = statDAO.popularDestinations();
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> iter = results.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append("----");
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
	}
	
	public void getUserWithNoTrip() {
		List<User> results = statDAO.usersWithNoTrips();
		Iterator<User> it = results.iterator();
	    while(it.hasNext()){
	        System.out.println(it.next());
	    }
	}
	
	public void getAirlinesWithMoreThan() {
		Scanner scan = new Scanner(System.in);
		int targetAmount = 0;
		System.out.print("More than how many planes would you like to see: ");
		String temp = scan.nextLine();
		targetAmount = Integer.parseInt(temp);
		Map<String, Integer> results = statDAO.airlinesWithMoreThan(targetAmount);
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> iter = results.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append("----");
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
        scan.close();
	}
	
	public void getAboveAVGCapcity() {
		List<Plane> results = statDAO.planesWithGreaterCapacityThanAverage();
		Iterator<Plane> it = results.iterator();
	    while(it.hasNext()){
	        System.out.println(it.next());
	    }
	}
}
