package edu.sjsu.cs157a;

import edu.sjsu.cs157a.controllers.SearchController;

public class App {
	
	public static void main(String[] args) {
		System.out.println("Hello, World");
		
		SearchController sc = new SearchController();
		sc.run();
		
	}
}
