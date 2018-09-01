package com.bst.user.entities.test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bst.user.registration.entities.RegistrationToken;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistrationToken.class)
public class RegistrationTokenTest {

	@BeforeClass
	public static void startSnapshot() throws Exception {
		start();
	}

	@AfterClass
	public static void stopSnapshot() throws Exception {
		validateSnapshots();
	}
	
	@Before
	public void setupDate() throws Exception {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("PST"));

	    Date NOW = sdf.parse("2015-05-23 00:00:00");
 
	    whenNew(Date.class).withNoArguments().thenReturn(NOW);
	}

	@Test
	public void testRegistrationToken() {
		RegistrationToken token = new RegistrationToken();
		expect(token).toMatchSnapshot();
	}

	@Test
	public void testRegistrationTokenString() {
		RegistrationToken token = new RegistrationToken("some-email");
		expect(token).toMatchSnapshot();
	}

	@Test
	public void testGetEmail() {
		RegistrationToken token = new RegistrationToken("some-email");
		expect(token.getEmail()).toMatchSnapshot();
	}

}
