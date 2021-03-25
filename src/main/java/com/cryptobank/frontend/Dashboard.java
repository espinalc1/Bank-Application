package com.cryptobank.frontend;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cryptobank.models.BankAccount;
import com.cryptobank.models.Transaction;
import com.cryptobank.models.Transfer;
import com.cryptobank.models.User;
import com.cryptobank.models.UserGroup;

public class Dashboard {
	private enum UserGroups {
		EMPLOYEE(UserGroup.employee), CUSTOMER(UserGroup.customer);

		private final UserGroup userGroup;

		UserGroups(UserGroup userGroup) {
			this.userGroup = userGroup;
		}
	}

	private static Logger log = Logger.getLogger(Dashboard.class);
	private User current_user;
	private UserGroups current_user_group;

	private Dashboard() {
		// this is mostly used for testing purposes
	};

	public static Dashboard getDashboard() {
		Dashboard db = new Dashboard();
		return db;
	}

	public Dashboard(User current_user, Scanner sc) {
		super();
		this.current_user = current_user;
		this.current_user_group = current_user.getGroup().equals(UserGroups.CUSTOMER.userGroup) ? UserGroups.CUSTOMER
				: UserGroups.EMPLOYEE;

		switch (this.current_user_group) {
		case EMPLOYEE:
			employeeDashboard(sc, this.current_user);
			break;
		case CUSTOMER:
			customerDashboard(sc, this.current_user);
			break;
		default:
			break;
		}

	}

	public void customerDashboard(Scanner sc, User user) {

		boolean run = true;
		do {
			log.info("Please choose from the following options:\n" + "		* Press 1 to apply for a bank account\n"
					+ "		* Press 2 to check your account(s)\n");
			switch (sc.nextLine()) {
			case "1":
				CreateAccountDashboard(sc, user);
				break;
			case "2":
				AccountsDashboard(sc, user);
				break;
			default:
				log.info("Sorry, try again!\n");
				break;
			}
			log.info("If you want to Create Accounts or View Your Accounts press 1!\n" + "		* Press 1 to continue\n"
					+ "		* or anything else to go to the EMPLOYEE DASHBOARD!\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);
	}

	private void CreateAccountDashboard(Scanner sc, User user) {
		// TODO Auto-generated method stub
		// account creation
		boolean run = true;
		do {
			log.info("Would you like to create an account?\n" + "		* Press 1 to create an account\n"
					+ "		* Press anything else to quit to the MAIN DASHBOARD\n");
			// validate details =>
			// set account in the user model =>
			// implement dao for account =>
			String choice = sc.nextLine();

			switch (choice) {
			case "1":
				// create account
				current_user.addAccount(BankAccount.createAccount(user));
				break;
			default:
				break;
			}

			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to exit to the MAIN DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	private void AccountsDashboard(Scanner sc, User user) {
		// see accounts that are pending
		log.info(user.getUserName() + "'s WALLETS\n");
		// refreshes entire account list
		user.setAccounts(BankAccount.getUserAccounts(user));
		// see accounts that can be used
		log.info("PENDING WALLETS\n");
		List<BankAccount> ba = user.getAccounts();
		ba.stream().filter(b -> b.account_status() == false).forEach(b -> log.info("	" + b));

		log.info("\nACTIVE ACCOUNTS\n");
		// do something with approved accounts
		boolean run = true;
		do {
			log.info("Enter the Account Number of the Bank Account you'd like to see!\n"
					+ "	* Press anything else to exit\n");

			ba.stream().filter(b -> b.account_status() == true).forEach(b -> log.info("	" + b.toString()));

			// this could be turned into a validator of some sort
			int account_number = 0;
			try {
				account_number = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("... Leaving!");
				break;
			}

			BankAccount selected = null;
			// get the right bank account
			for (BankAccount b : ba) {
				if (b.getAccount_number().equals(account_number))
					selected = b;
			}

			if (selected == null) {
				log.info("Please try again!");
			} else {
				transactionDashBoard(sc, user, selected);
			}

			log.info("\nWould you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to go to the MAIN DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;
		} while (run);

	}

	public void transactionDashBoard(Scanner sc, User user, BankAccount ba) {
		boolean run = true;
		do {
			log.info("Welcome to the Transaction dashboard, " + user.getUserName() + "\n"
					+ "		* Press 1 to withdraw from this account!\n"
					+ "		* Press 2 to Transfer Money to another account!\n"
					+ "		* Press 3 to view Pending Withdrawals\n" + "		* Press 4 to view Transaction History"
					+ "Current Account: \n" + "		" + ba.toString());

			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				withDrawDashboard(sc, user, ba);
				break;
			case "2":
				transferFundsDashboard(sc, user, ba);
				break;
			case "3":
				approveIncomingTransfers(sc, user, ba);
				break;
			case "4":
				transactionHistoryDashboard(sc, user, ba);
				break;
			default:
				break;
			}

			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to go to the ACCOUNTS DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void withDrawDashboard(Scanner sc, User user, BankAccount ba) {
		boolean run = true;
		do {
			log.info("\nHow much would you like to withdraw from your account?\n");

			Double amount = 0d;
			try {
				amount = Double.parseDouble(sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("Sorry, that number was incorrect!\n");
				break;
			}

			// withdrawal is also called a transaction here
			Transaction tran = new Transaction(ba, amount);
			if (Transaction.makeTransaction(tran) > 0) {
				log.info("The following transaction was successful: \n" + "		" + tran + "\n");
			} else {
				log.info("Sorry, the transaction wasn't successful!\n");
			}

			// then make the transaction happen

			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to exit to the TRANSACTIONS DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void transferFundsDashboard(Scanner sc, User user, BankAccount ba) {
		boolean run = true;
		do {
			log.info("Transfer Funds dashboard\n");

			log.info("Enter the account number that you will transfer to:\n"
					+ "		* An invalid number will cause you to exit\n");
			// check to see if an account exists
			// exit if not a good value
			BankAccount receiver = null;
			int account_number = 0;
			try {
				account_number = Integer.parseInt(sc.nextLine());
				receiver = BankAccount.getAccountById(account_number);
				if (receiver == null) {
					log.info("This account doesn't exist. Please try again!\n");
					break;
				}
			} catch (NumberFormatException e) {
				log.info("Account number's don't have letters!\n");
				break;
			}

			// enter an amount
			log.info("Now please enter an amount!\n");
			double amount = 0.00;
			try {
				amount = Double.parseDouble(sc.nextLine());
				if (amount < 0) {
					log.info("Sorry, this is not a valid amount for a transaction!");
					break;
				}
			} catch (NumberFormatException e) {
				log.info("No letters please!\n");
				break;
			}

			// make the transaction happen
			log.debug(ba.getAccount_user().getUserId());

			Transfer tran = new Transfer(ba, receiver, amount);
			int transaction_id = Transfer.makeTransaction(tran);
			if (transaction_id > 0) {
				log.info(
						"The transfer payment to the following account!:\n" + "		Receiver " + receiver.toString());
				tran.setTransaction_id(transaction_id);
			} else {
				log.info("I'm sorry, the transfer payment wasn't successful!\n");
			}

			log.info("Would you like to try again or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to return to the TRANSACTION DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void approveIncomingTransfers(Scanner sc, User user, BankAccount ba) {
		boolean run = true;
		do {
			log.info("INCOMING TRANSFERS DASHBOARD, " + user.getUserName() + "\n");

			// check to see if an account exists
			// exit if not a good value
			// a method to get all of the Users' incoming transfers
			List<Transfer> trans = Transfer.getTransfersByUser(user);
			int i = 0;
			for (Transfer tran : trans) {
				log.info("		index =" + i + "  " + tran.toString());
				i++;
			}

			// select the transfer to approve
			log.info("\nType in the index of the Transfer you'd like to approve or reject:\n");
			int transfer_index = 0;
			Transfer selected_tran;
			try {
				transfer_index = Integer.parseInt(sc.nextLine());
				if (transfer_index >= 0 && transfer_index < i) {
					selected_tran = trans.get(transfer_index);
				} else {
					log.info("Sorry but that was an invalid choice. Try again!\n");
					break;
				}
			} catch (NumberFormatException e) {
				log.info("Account number's don't have letters! Please try again!\n");
				break;
			}

			log.info("What would you like to do with the following account:\n" + selected_tran + "\n"
					+ "			* Press 1 to Approve this transfer\n" + "			* Press 2 to Reject this transfer\n"
					+ "			* Press any other button to go back\n");
			String status = sc.nextLine();
			switch (status) {
			case "1":
				log.info("You have chosen to ACCEPT this transfer\n");
				Transfer.changeTransferStatus(selected_tran, "completed");
				break;
			case "2":
				log.info("You have chosen to REJECT this transfer\n");
				Transfer.changeTransferStatus(selected_tran, "rejected");
				break;
			default:
				break;
			}

			// A method to approve of the incoming transaction
			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to return to the TRANSACTION DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void transactionHistoryDashboard(Scanner sc, User user, BankAccount ba) {
		boolean run = true;
		do {
			log.info("TRANSACTION HISTORY DASHBOARD, " + user.getUserName() + "\n");

			List<Transaction> trans = Transaction.getTransactionsByUser(user);
			trans.stream().forEach(tran -> log.info("	Time 1  " + tran));

			log.info("\nWould you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to return to the TRANSACTION DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void employeeDashboard(Scanner sc, User user) {

		boolean run = true;
		do {
			log.info("Welcome to the employee dashboard, " + user.getUserName() + "\n"
					+ "		* Press 1 to View Bank Acounts in the Crypto Bank System\n"
					+ "		* Press 2 to Approve New Customer Accounts\n"
					+ "Also, remember, that as an employee you automatically generate an Account\n"
					+ "		* Press 3 to access your personal customer dashbaord\n"
					+ "If you want to quit, enter any other button.\n");

			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				log.info("View Customer Accounts");
				CustomerDataBase(sc, user);
				break;
			case "2":
				log.info("Approve New Customer Accounts");
				PendingAccountsDashboard(sc, user);
				break;
			case "3":
				customerDashboard(sc, user);
				break;
			default:
				break;
			}

			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to exit\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}

	public void PendingAccountsDashboard(Scanner sc, User user) {
		log.info("Welcome to the PENDING ACCOUNTS DASHBOARD, " + user.getUserName() + "\n"
				+ "		* You can approve of Pending Accounts in this dashboard\n"
				+ "		* Enter the account number to approve:\n"
				+ "		* Enter anything else to go to the EMPLOYEE DASHBOARD\n");

		// accesses the db
		List<BankAccount> pending = BankAccount.getAllPendingAccounts();
		boolean run = true;
		do {
			if (pending == null || pending.isEmpty()) {
				log.info("Sorry, there are no accounts!\n");
			} else {
				int i = 0;
				for (BankAccount b : pending) {
					log.info("index " + i + " " + b.toString());
					i++;
				}

				log.info("\nEnter the index of the account you'd like to approve or delete here: \n"
						+ "		* Press any other number to quit to the EMPLOYEE DASHBOARD!\n");

				int index = 0;
				try {
					index = Integer.parseInt(sc.nextLine());
					if (index < 0 || index > i)
						break;
				} catch (NumberFormatException e) {
					log.info("... Leaving\n");
					break;
				}

				// approve in db
				// get account
				BankAccount selected_account = pending.get(index);

				log.info("What would you like to do to this account?\n" + "		* Enter 1 to approve\n"
						+ "		* Enter 2 to delete\n"
						+ "		* Enter anything else to exit to the MAIN EMPLOYEE DASHBOARD\n");
				if (sc.nextLine().equals("1")) {
					int c = BankAccount.approveAccount(selected_account.getAccount_number());
				} else if (sc.nextLine().equals("2")) {
					int c = BankAccount.deleteAccount(selected_account.getAccount_number());
					// delete from this list, not from the db.
					// Db deletion occurs above
					pending.remove(selected_account);
				}

			}

			log.info("Would you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 		* Press anything else to exit to the MAIN EMPLOYEE DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}
	// get user info
	// depending on the user group, they can have access to different options

	private void AccountsDashboardEmployeeView(Scanner sc, User user) {
		// see accounts that are pending
		log.info(user.getUserName() + "'s BANK ACCOUNTS\n");
		// refreshes entire account list
		user.setAccounts(BankAccount.getUserAccounts(user));
		// see accounts that can be used
		log.info("PENDING ACCOUNTS\n");
		List<BankAccount> ba = user.getAccounts();
		ba.stream().filter(b -> b.account_status() == false).forEach(b -> log.info("	" + b));

		log.info("\nACTIVE ACCOUNTS\n");

		int i = 0;
		for (BankAccount b : ba) {
			log.info("Index " + i + " " + b.toString());
			i++;
		}
	}

	public void CustomerDataBase(Scanner sc, User user) {

		boolean run = true;
		do {
			log.info("Hello, " + user.getUserName() + ". In this dashboard you'll be able to view\n"
					+ "customer accounts in our database! To view a customer's account please select from\n"
					+ "the following list:\n");
			List<User> user_list = User.getCustomers();
			int i = 0;
			for (User u : user_list) {
				log.info("Index " + i + " " + u.toString());
				i++;
			}

			log.info("\nEnter the index of the user to see their account!\n"
					+ "			* Press anything else to exit to the MAIN EMPLOYEE DASHBOARD\n");

			int index = 0;
			try {
				index = Integer.parseInt(sc.nextLine());
				if (index < 0 || index > i)
					break;
			} catch (NumberFormatException e) {
				log.info("... Leaving!\n");
				break;
			}

			User selected_user = user_list.get(index);
			AccountsDashboardEmployeeView(sc, selected_user);
			log.info("\nWould you like to continue or exit?\n" + "		* Press 1 to continue\n"
					+ " 	* Press anything else to exit to the MAIN EMPLOYEE DASHBOARD\n");
			run = sc.nextLine().equals("1") ? true : false;

		} while (run);

	}
}
