package com.cryptobank.frontend;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cryptobank.DAO.UserDAO;
import com.cryptobank.DAOImpl.UserDAOImpl;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;
import com.cryptobank.models.exceptions.UserException;
import com.cryptobank.models.validators.Validator;

public class UserCreatorPrompt {
	public static User this_user = new User();
	public static Logger log = Logger.getLogger(UserCreatorPrompt.class);
	public static Scanner sc = new Scanner(System.in);

	public static void setAccountType() {
		log.info("We're so glad that you're taking the first steps to modernize your banking.\n"
				+ "Once again, welcome to the blockchain!\n");
		// choose bank account type
		boolean stage0 = true;
		do {
			log.info("What type of account are you creating today?\n" + "Enter 1 for Customer Account\n"
					+ "Enter 2 for *Employee Account\n" + "		* You can apply for a bank account in your dashboard!");

			// ask for input

			String choice = sc.nextLine();
			// if user name successful then the loop will break

			if (choice.equals("1")) {
				this_user.setGroup(UserGroup.employee);
				stage0 = false;
			} else if (choice.equals("2")) {
				this_user.setGroup(UserGroup.customer);
				stage0 = false;
			} else {
				log.info("That is not a valid choice. Please try again!");
			}

			// stage0 = this_user.setUserName(user_name);
			// set the group for this user
			// set the group for
		} while (stage0);

	}

	public static void setUserName() {

		// user name
		boolean stage1 = true;
		do {
			log.info("Please enter a username.\nIf it's not unique, we'll let you know:\n");

			// ask for input
			String user_name = sc.nextLine();
			// if user name successful then the loop will break
			// will return false if the name is unique

			try {
				// make a validator
				// pattern
				String pattern = ".{8,15}";
				// message
				String message = "Sorry, try again. However, follow these rules\n"
						+ "		* This must be between 8 and 15 characters long.\n"
						+ "		* Also, if it's taken, we'll let you know.\n";
				Validator nameValidator = new Validator(message, pattern);
				nameValidator.validate(user_name);
				// check to see if it's available

				try {
					String uniqueMessage = "This user name is already taken. Please try again";
					Validator nameUnique = new Validator(uniqueMessage);
					nameUnique.validateUnique("user_name", user_name);
					this_user.setUserName(user_name);
					stage1 = false;

				} catch (UserException e) {
					log.info(e.getMessage());
				}

			} catch (UserException e) {
				log.info(e.getMessage());
			}

		} while (stage1);
	}

	public static void setEmail() {
		// email
		boolean stage2 = true;
		do {
			log.info("Please enter an email address.\nIf it's not unique, we'll let you know:\n");

			// ask for input
			String user_email = sc.nextLine();
			// if user name successful then the loop will break
			try {
				// make a validator
				// pattern
				String pattern = "[a-zA-Z][a-zA-Z0-9\\.]+@[a-zA-Z]{3,10}\\.(com||org||edu||net)";
				// message
				String message = "Sorry, try again. However, follow these rules\n"
						+ "		* Your email address must be valid.\n";
				Validator validator = new Validator(message, pattern);
				validator.validate(user_email);
				// check to see if it's available

				try {
					String uniqueMessage = "This email is already taken. Please try again";
					Validator unique = new Validator(uniqueMessage);
					unique.validateUnique("email", user_email);
					this_user.setEmail(user_email);
					stage2 = false;
				} catch (UserException e) {
					log.info(e.getMessage());
				}

			} catch (UserException e) {
				log.info(e.getMessage());
			}

		} while (stage2);

	}

	public static void setPassword() {
		// password
		boolean stage3 = true;
		do {
			log.info("Please enter a password! Here are the rules: \n"
					+ "		* Your password must be between 8 and 15 characters long\n"
					+ "		* It can include any of the following characters:\n"
					+ "				* upper and lower case characters and numbers.\n" + "				* !@#$%^&*");

			String password1 = sc.nextLine();
			// check to see if it follows the above rules:

			log.info("Now, one more time!");
			String password2 = sc.nextLine();

			// will have to build validation here
			// make sure that both are equal
			String message = "Your password is not valid. Please try again!";
			String pattern = "[A-Za-z0-9\\!@#\\$%^&*\\.]{8,}";

			Validator validator = new Validator(message, pattern);
			try {
				validator.validate(password1);
			} catch (UserException e) {
				log.info(e.getMessage());
			}

			if (!password1.equals(password2)) {
				log.info("Your passwords weren't equal. Please try again!");
			} else {
				this_user.setPassword(password2);
				stage3 = true;
			}

		} while (!stage3);

	}

	public static void createUser() {
		// save this user to the db
		// use Dao to add this person to the database
		UserDAO dao = new UserDAOImpl();
		int user_id = dao.createUser(this_user); // creates user and returns generated user id

		// make them login to their account
		this_user.setUserId(user_id);

		log.info(this_user.toString());

		// how do i assign the user to a group in the database?
		UserGroup.addToGroup(this_user);
		//
		log.info("If you'd like, try to login with your new username and password!");

	}

	public static void createUserPrompt(Scanner sc) {
		setAccountType();
		setUserName();
		setEmail();
		setPassword();
		createUser();
	};
}
