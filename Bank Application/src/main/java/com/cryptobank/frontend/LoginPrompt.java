package com.cryptobank.frontend;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cryptobank.models.BankAccount;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;

public class LoginPrompt {
	private User current_user;
	private static Logger log = Logger.getLogger(LoginPrompt.class);

	public LoginPrompt(Scanner sc) {

		User userIncomplete = null;
		boolean signedOff = true;
		do {
			log.info("Please type in your username:");
			String this_username = sc.nextLine();
			log.info("Great, now enter your password!");
			String this_password = sc.nextLine(); 

			// ask to login with username and password
			// they should be forced to put in both
			userIncomplete = User.getUser(this_username, this_password);

			if (userIncomplete == null || !this_password.equals(userIncomplete.getPassword())) {
				log.info("This username-password combo doesn't exist! Please try again!");
			} else {
				signedOff = false;
			}

		} while (signedOff);

		// use the user object to encapsulate all of the current user's data
		this.current_user = completeUser(userIncomplete);

		log.info("\nWelcome back to the Blockchain, " + this.current_user.getUserName());
	}

	// this ensures that the logged in user's details are all in one place for
	// future use

	// should this just go in the user page
	private User completeUser(User user) {
		// get the group
		user.setGroup(UserGroup.getUserGroup(user));
		user.setAccounts(BankAccount.getUserAccounts(user));
		return user;
	}

	public User getCurrent_user() {
		return this.current_user;
	}

}
