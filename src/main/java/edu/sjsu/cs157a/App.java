package edu.sjsu.cs157a;
import java.sql.Date;
import java.util.Scanner;

import javax.security.sasl.AuthenticationException;

import edu.sjsu.cs157a.models.User;
import edu.sjsu.cs157a.controllers.AdminFunctionsController;
import edu.sjsu.cs157a.controllers.SearchController;
import edu.sjsu.cs157a.controllers.UserAuthController;

public class App {

	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		User tempUI = new User();
		String inputHolder = "";
		
		tempUI = userAuth();
		if(tempUI != null) {
			while(true) {
				// logged in user
				System.out.println("\n\nYou have just logged in.");
				System.out.println("What feature would you like to use:");
				System.out.println("[1] Search a flight;");
				System.out.println("[2] Show all trips;");
				System.out.println("[3] Update account information;");
				System.out.println("[4] Delete your account;");
				System.out.println("[5] Add a trip;");
				if(tempUI.getIsAdmin()) {
					System.out.println("**** Admin Features ****");
					System.out.println("[6] Show most popular departing airports;");
					System.out.println("[7] Show most popular destination airports;");
					System.out.println("[8] Show most popular airlines;");
					System.out.println("[9] Show users with no trip records;");
					System.out.println("[10] Show total amount of planes for a airline;");
					System.out.println("[11] Show all users that are above the average user age;");
				}
				System.out.println("[0] Exit.");
				inputHolder = scan.nextLine();
				if(Integer.parseInt(inputHolder) != 0) {
					if(tempUI.getIsAdmin()) {
						if(adminFunctions(inputHolder, tempUI) == false) break;
					}else {
						if(noneAdminFunctions(inputHolder, tempUI) == false) break;
					}
				}
				else break;
			}
		}
		else {
			// user not logged in
			System.out.println("You will have to loggin to use all the features");
			System.out.println("Enter '0' to end the application.");
			inputHolder = scan.nextLine();
			if(Integer.parseInt(inputHolder) == 0);
			
		}
		System.out.println("Thanks for using our application. Bye!");
		
	}
	
	static boolean adminFunctions(String inputHolder, User tempUI) {
		if(Integer.parseInt(inputHolder) == 1) {
			searchFlight();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 2) {
			listAllTrips(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 3) {
			updateAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 4) {
			deleteAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 5) {
			addATrip(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 6) {
			mostDepartingAirport();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 7) {
			mostDestinationAirport();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 8) {
			mostAirline();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 9) {
			userWithNoTrip();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 10) {
			AirlinesWithMoreThan();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 11) {
			AboveAVGCapcity();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 0) {
			return false;
		}
		else{
			System.out.println("Invalid input, please try agin.");
			return true;
		}
	}
	
	static boolean noneAdminFunctions(String inputHolder, User tempUI) {
		if(Integer.parseInt(inputHolder) == 1) {
			searchFlight();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 2) {
			listAllTrips(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 3) {
			updateAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 4) {
			deleteAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 5) {
			addATrip(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 0) {
			return false;
		}
		else{
			System.out.println("Invalid input, please try agin.");
			return true;
		}
	}
	
	static User userAuth() {
		boolean isDone = false;
		String inputHolder = "";
		User tempUI = null;
		while(!isDone){
			scan.reset();
			System.out.println("Do you want to:");
			System.out.println("[1] Sign In");
			System.out.println("[2] Register");
			System.out.println("[3] Exit\n");
			if(scan.hasNextLine())
				inputHolder = scan.nextLine();
			// ****************
			// User Sign in
			if(Integer.parseInt(inputHolder) == 1){
				UserAuthController uc = new UserAuthController();
				tempUI = uc.Login();
				return tempUI;
			}
			
			// *********************
			// REGISTER NEW USER
			else if(Integer.parseInt(inputHolder) == 2){
				UserAuthController uc = new UserAuthController();
				int uID = uc.Register();
				System.out.println("Welcome, this is your user ID: " + uID);
			}
			else if(Integer.parseInt(inputHolder) == 3){
				isDone = true;
			}
			else{
				System.out.println("Invalid Selection, Please Try Again...");
				System.out.println("Do you want to:");
				System.out.println("[1] Sign In");
				System.out.println("[2] Register\n");
				inputHolder = scan.nextLine();
			}
			
		}
		return tempUI;
	}
	
	static void searchFlight() {
		SearchController sc = new SearchController();
		sc.run();
	}
	
	static void listAllTrips(User user) {
		UserAuthController uc = new UserAuthController();
		uc.showTrips(user);
	}
	
	static void updateAccount(User tempUI) {
		String inputHolder;
		while(true) {
			System.out.println("Here is your account information:");
			System.out.println(tempUI.toString());
			System.out.println("\nWhat would you like to change?");
			System.out.println("[1] First Name");
			System.out.println("[2] Last Name");
			System.out.println("[3] Email Address");
			System.out.println("[4] Password");
			System.out.println("[5] Birth Date");
			System.out.println("[6] Age");
			System.out.println("[7] Admin Status");
			System.out.println("[0] Save and Exit.");
			inputHolder = scan.nextLine();
			if(Integer.parseInt(inputHolder) == 1) {
				System.out.println("Please Enter your first name:");
				inputHolder = scan.nextLine();
				tempUI.setFname(inputHolder);
			}
			else if(Integer.parseInt(inputHolder) == 2) {
				System.out.println("Please Enter your last name:");
				inputHolder = scan.nextLine();
				tempUI.setLname(inputHolder);
			}
			else if(Integer.parseInt(inputHolder) == 3) {
				System.out.println("Please Enter your email address:");
				inputHolder = scan.nextLine();
				tempUI.setEmail(inputHolder);
			}
			else if(Integer.parseInt(inputHolder) == 4) {
				System.out.println("Please Enter your password:");
				inputHolder = scan.nextLine();
				tempUI.setPassword(inputHolder);
			}
			else if(Integer.parseInt(inputHolder) == 5) {
				System.out.println("Please Enter your birth date:");
				inputHolder = scan.nextLine();
				tempUI.setBirthDate(Date.valueOf(inputHolder));
			}
			else if(Integer.parseInt(inputHolder) == 6) {
				System.out.println("Please Enter your age:");
				inputHolder = scan.nextLine();
				tempUI.setAge(Integer.parseInt(inputHolder));
			}
			else if(Integer.parseInt(inputHolder) == 7) {
				System.out.println("Please Enter your admin satus:");
				inputHolder = scan.nextLine();
				while(true) {
					System.out.println("[1] Yes");
					System.out.println("[2] No");
					inputHolder = scan.nextLine();
					if(Integer.parseInt(inputHolder) == 1) {
						tempUI.setIsAdmin(true);
						break;
					}
					else if(Integer.parseInt(inputHolder) == 2) {
						tempUI.setIsAdmin(false);
						break;
					}
					else System.err.println("Invalid Input, try again.");
				}
			}
			else if(Integer.parseInt(inputHolder) == 0) {
				break;
			}
			else System.out.println("Invalid Input, try again.");
		}
	}
	
	static void deleteAccount(User tempUI) {
		UserAuthController uc = new UserAuthController();
		uc.removeUser(tempUI);
	}

	static void addATrip(User tempUI) {
		UserAuthController uc = new UserAuthController();
		uc.addNewTrip(tempUI);
	}
	
	static void mostDepartingAirport() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getPopDepartures();
	}
	
	static void mostDestinationAirport() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getPopDestination();
	}
	
	static void mostAirline() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getPopAirlines();
	}
	
	static void userWithNoTrip() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getUserWithNoTrip();
	}
	
	static void AirlinesWithMoreThan() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getAirlinesWithMoreThan();
	}
	
	static void AboveAVGCapcity() {
		AdminFunctionsController afc = new AdminFunctionsController();
		afc.getAboveAVGCapcity();
	}
}