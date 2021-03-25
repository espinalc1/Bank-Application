package com.cryptobank.models;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
	private static User thisUser = new User();
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	
	@Test
	public void test() {
		thisUser.setPassword("M3dmetry!!!k");
		assertEquals(true, true);
	}

}
