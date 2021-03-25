package com.cryptobank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cryptobank.frontend.Dashboard;
import com.cryptobank.frontend.LoginPrompt;
import com.cryptobank.frontend.UserCreatorPrompt;
import com.cryptobank.models.User;

public class Main {
	private static Logger log = Logger.getLogger(Main.class);
	private static User logged_in_user = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.info("Hello, Welcome to another Crypto Bank!\n" + "Better yet, welcome to the Blockchain!\n");
		Scanner sc = new Scanner(System.in);

		boolean keepRunning = true;

		// overall program
		while (keepRunning) {

			// should start running this program if there's no logged in user
			while (logged_in_user == null) {
				log.info("What would you like to do today?\n" + "Press 1 to Login\n"
						+ "Press 2 to Create a New Cryptocurrency Wallet\n");
				String choice = sc.nextLine();

				switch (choice) {
				case "1":
					// When the user logs in, their data is refreshed
					LoginPrompt login = new LoginPrompt(sc);
					setLogged_in_user(login.getCurrent_user());
					break;
				case "2":
					UserCreatorPrompt.createUserPrompt(sc);
					log.info("Would you like to continue or leave?\n" + "Press \"1\" to continue.\n"
							+ "Enter any other key to leave!\n");
					if (!sc.nextLine().equals("1")) {
						keepRunning = false;
					}
					break;
				default:
					log.info("Sorry, that is not a correct command.\n");
					break;
				}
			}

			// get to customer dashboard if only a customer,
			// if an employee, should be given an option
			log.debug("....Loading your dashboard!");
			while (logged_in_user != null) {
				// Dashboard prompt would go here
				log.info("Hello, " + logged_in_user.getUserName() + ".\nDashboard\n\n");

				Dashboard userDashboard = new Dashboard(logged_in_user, sc);

				log.info("Would you like to continue or leave?\n" + "Press \"1\" to continue.\n"
						+ "Enter any other key to leave!\n");
				if (!sc.nextLine().equals("1")) {

					keepRunning = false;
					break;
				}
			}
			log.debug("Reaching the break for break purposes");
			break;

		} // overall program keep running loop
			// exiting program
		log.info("Take care!");
		sc.close();
	}

	public static User getLogged_in_user() {
		return logged_in_user;
	}

	public static void setLogged_in_user(User logged_in_user) {
		Main.logged_in_user = logged_in_user;
	}

}
