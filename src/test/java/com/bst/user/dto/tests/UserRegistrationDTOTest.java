package com.bst.user.dto.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bst.user.registration.dto.UserRegistrationDTO;

import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;

public class UserRegistrationDTOTest {
	
	@BeforeClass
	public static void startSnapshot() {
		start();
	}
	
	@AfterClass
	public static void stopSnapshot() {
		validateSnapshots();
	}

	@Test
	public void testDefaultConstruction() {
		UserRegistrationDTO userDTO = new UserRegistrationDTO();
		expect(userDTO).toMatchSnapshot();
	}
	
	@Test
	public void testFields() {
		UserRegistrationDTO userDTO = new UserRegistrationDTO();
		userDTO.setEmail("some-email");
		userDTO.setReCaptchaResponse("some-captcha");
		expect(userDTO).toMatchSnapshot();
	}

}
