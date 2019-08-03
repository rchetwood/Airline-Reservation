package edu.sjsu.cs157a;

import edu.sjsu.cs157a.controllers.LoginController;
import edu.sjsu.cs157a.controllers.SearchController;
import edu.sjsu.cs157a.models.User;

public class App {
	
	public static void main(String[] args) {
		System.out.println("Hello, World");
		
		LoginController lc = new LoginController();
		SearchController sc = new SearchController();
		User currentUser = null;
		
		
		currentUser = lc.login();
		
		System.out.print(currentUser);
		
		sc.run();
		
	}
}
