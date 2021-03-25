package com.cryptobank.models.exceptions;

public class AccountExceptions {

	public class AccountException extends RuntimeException {
		private String message;

		public AccountException(String message) {
			super();
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
