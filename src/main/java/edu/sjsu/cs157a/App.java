package edu.sjsu.cs157a;
import java.sql.Date;
import java.util.Scanner;

import javax.security.sasl.AuthenticationException;

import edu.sjsu.cs157a.models.User;
 

import edu.sjsu.cs157a.controllers.SearchController;
import edu.sjsu.cs157a.controllers.UserAuthController;

public class App {
	
	public static void main(String[] args) {
		User tempUI = new User();
		String inputHolder = "";
		Scanner scan = new Scanner(System.in);
		
		
		while(true) {
			if(userAuth(tempUI)) {
				// logged in user
				System.out.println("You have just logged in.");
				System.out.println("What feature would you like to use:");
				System.out.println("[1] Search a flight;");
				System.out.println("[2] Show all trips;");
				System.out.println("[3] Update account information;");
				System.out.println("[4] Delete your account;");
				if(tempUI.getIsAdmin()) {
					System.out.println("**** Admin Features ****");
					System.out.println("[5] Show most popular departing airports;");
					System.out.println("[6] Show most popular destination airports;");
					System.out.println("[7] Show most popular airlines;");
					System.out.println("[8] Show most popular planes;");
					System.out.println("[9] Show total amount of planes for a airline;");
					System.out.println("[10] Show all users that are above the average user age;");
				}
				System.out.println("[0] Exit.");
				inputHolder = scan.nextLine();
				if(tempUI.getIsAdmin()) {
					if(adminFunctions(inputHolder, tempUI) == false) break;
				}else {
					if(noneAdminFunctions(inputHolder, tempUI) == false) break;
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
		scan.close();
	}
	
	static boolean adminFunctions(String inputHolder, User tempUI) {
		if(Integer.parseInt(inputHolder) == 1) {
			searchFlight();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 2) {
			listAllTrips();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 3) {
			updateAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 4) {
			deleteAccount();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 5) {
			mostDepartingAirport();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 6) {
			mostDestinationAirport();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 7) {
			mostAirline();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 8) {
			mostPlanes();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 9) {
			totalPlanes();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 10) {
			aboveAvgUsers();
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
			listAllTrips();
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 3) {
			updateAccount(tempUI);
			return true;
		}
		else if(Integer.parseInt(inputHolder) == 4) {
			deleteAccount();
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
	
	static boolean userAuth(User tempUI) {
		boolean isDone = false;
		String inputHolder = "";
		Scanner scan = new Scanner(System.in);
		while(!isDone){
			System.out.println("Do you want to:");
			System.out.println("[1] Sign In");
			System.out.println("[2] Register");
			System.out.println("[3] Exit");
			inputHolder = scan.nextLine();
			// ****************
			// User Sign in
			if(Integer.parseInt(inputHolder) == 1){
				while(true) {
					// keep trying to log in until user logs in or 
					// user wants to quite.
					System.out.println("Please enter your email address: ");
					inputHolder = scan.nextLine();
					tempUI.setEmail(inputHolder);
					System.out.println("Please enter your password: ");
					inputHolder = scan.nextLine();
					tempUI.setPassword(inputHolder);
					
					// User Has been verified
					try {
						if(verifyUser(tempUI)) return true;
						else {
							System.out.println("Would you like to try again?");
							System.out.println("[1] Yes.");
							System.out.println("[2] No.");
							inputHolder = scan.nextLine();
							if(Integer.parseInt(inputHolder) == 2) break;
						}
					} catch (AuthenticationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			// *********************
			// REGISTER NEW USER
			else if(Integer.parseInt(inputHolder) == 2){
				tempUI = new User();
				System.out.println("Please enter your first name: ");
				inputHolder = scan.nextLine();
				tempUI.setFname(inputHolder);
				System.out.println("Please enter your last name: ");
				inputHolder = scan.nextLine();
				tempUI.setLname(inputHolder);
				System.out.println("Please enter your email Address: ");
				inputHolder = scan.nextLine();
				tempUI.setEmail(inputHolder);
				System.out.println("Please enter your password: ");
				inputHolder = scan.nextLine();
				tempUI.setPassword(inputHolder);
				System.out.println("Please enter your birth date: (yyyy-mm-dd) ");
				inputHolder = scan.nextLine();
				tempUI.setBirthDate(Date.valueOf(inputHolder));
				System.out.println("Please enter your age: ");
				inputHolder = scan.nextLine();
				tempUI.setAge(Integer.parseInt(inputHolder));
				while(true) {
					System.out.println("Please enter your admin status: ");
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
			else if(Integer.parseInt(inputHolder) == 3){
				isDone = true;
				break;
			}
			else{
				System.out.println("Invalid Selection, Please Try Again...");
				System.out.println("Do you want to:");
				System.out.println("[1] Sign In");
				System.out.println("[2] Register");
				inputHolder = scan.nextLine();
			}
			
		}
		scan.close();
		return false;
	}

	static boolean verifyUser(User tempUI) throws AuthenticationException {
		UserAuthController uc = new UserAuthController();
		if(uc.run(tempUI)) return true;
		else return false;
	}
	
	static void searchFlight() {
		SearchController sc = new SearchController();
		sc.run();
	}
	
	static void listAllTrips() {
		
	}
	
	static void updateAccount(User tempUI) {
		Scanner scan = new Scanner(System.in);
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

		scan.close();
	}
	
	static void deleteAccount() {
		
	}

	static void mostDepartingAirport() {
		
	}
	
	static void mostDestinationAirport() {
		
	}
	
	static void mostAirline() {
		
	}
	
	static void mostPlanes() {
		
	}
	
	static void totalPlanes() {
		
	}
	
	static void aboveAvgUsers() {
		
	}
}

