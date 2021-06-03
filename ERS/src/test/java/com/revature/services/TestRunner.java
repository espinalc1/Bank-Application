package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static Logger log = LogManager.getRootLogger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Result result = JUnitCore.runClasses(ManagerServicesTest.class);

		for (Failure failure : result.getFailures()) {
			log.debug(failure);
		}

		log.debug(result.wasSuccessful());
	}

}
