package com.bst.test.user.registration.rest;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bst.configuration.user.registration.UserRegistrationConfiguration;
import com.bst.test.user.registration.TestAppConfiguration;
import com.bst.test.user.registration.components.SecurityDisabler;
import com.bst.user.authentication.components.UserService;
import com.bst.user.registration.dto.UserRegistrationCompleteDTO;
import com.bst.user.registration.dto.UserRegistrationDTO;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.configuration.CaptchaSettings;
import com.bst.utility.services.EmailService;
import com.bst.utility.services.ReCaptchaService;
import com.bst.utility.testlib.SnapshotListener;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(listeners = SnapshotListener.class, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@ContextConfiguration(classes = { UserRegistrationConfiguration.class, TestAppConfiguration.class,
		SecurityDisabler.class })
@TestPropertySource("classpath:registration-controller-test.properties")
public class RegistrationRestControllerTest {
	@MockBean
	private CaptchaSettings captchaSettings;

	@MockBean
	private EmailService emailService;

	@Captor
	ArgumentCaptor<RegistrationToken> hashCaptor;

	@Captor
	ArgumentCaptor<Locale> locale;

	@MockBean
	private ReCaptchaService reCaptchaService;

	@Captor
	ArgumentCaptor<String[]> recepients;

	private HttpHeaders requestHeaders;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private UserService userService;

	@BeforeEach
	public void setup() {
		Mockito.when(this.captchaSettings.getKey()).thenReturn("test captcha key");
	}

	@Test
	public void testRegistrationStates() throws Exception {
		final ResponseEntity<UserRegistrationDTO> getResponse = this.testRestTemplate.getForEntity("/registration",
				UserRegistrationDTO.class);
		SnapshotListener.expect(getResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(getResponse.getBody()).toMatchSnapshot();

		final UserRegistrationDTO dto = new UserRegistrationDTO();
		dto.setCaptchaKey("test captcha key");
		dto.setEmail("john@doe.com");
		dto.setReCaptchaResponse("test captcha response");

		final ResponseEntity<Object> postResponse = this.testRestTemplate.exchange("/registration", HttpMethod.POST,
				new HttpEntity<>(dto, this.requestHeaders), Object.class);
		SnapshotListener.expect(postResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(postResponse.getBody()).toMatchSnapshot();

		final UserRegistrationCompleteDTO completeDto = new UserRegistrationCompleteDTO();
		completeDto.setConfirmPassword("password");
		completeDto.setPassword("password");

		Mockito.verify(this.emailService).sendMessage(this.recepients.capture(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				this.hashCaptor.capture(), this.locale.capture());
		completeDto.setHash(this.hashCaptor.getValue().getHash().toString());
		completeDto.setEmail("john@doe.com");
		completeDto.setName("John Doe");

		final ResponseEntity<Object> deleteResponse = this.testRestTemplate.exchange("/registration", HttpMethod.DELETE,
				new HttpEntity<>(completeDto, this.requestHeaders), Object.class);
		SnapshotListener.expect(deleteResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(deleteResponse.getBody()).toMatchSnapshot();
	}
}
