package edu.sjsu.cs157a.controllers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.sjsu.cs157a.DAOs.StatisticsDAO;

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
}
