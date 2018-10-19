package com.bst.test.user.registration.components;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bst.test.user.registration.TestAppConfiguration;
import com.bst.user.registration.components.RegistrationRestController;
import com.bst.user.registration.components.RegistrationService;
import com.bst.user.registration.dto.UserRegistrationCompleteDTO;
import com.bst.user.registration.dto.UserRegistrationDTO;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.configuration.CaptchaSettings;
import com.bst.utility.services.EmailService;
import com.bst.utility.services.ReCaptchaService;
import com.bst.utility.testlib.SnapshotListener;

@ExtendWith(SpringExtension.class)
@TestExecutionListeners(listeners = SnapshotListener.class, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DataJpaTest
@ContextConfiguration(classes = { TestAppConfiguration.class })
class RegistrationRestControllerTest {
	
	@TestConfiguration
	public static class RegistrationRestControllerTestConfiguration {
		@Bean
		public RegistrationRestController getRegistrationRestController() {
			return new RegistrationRestController();
		}
	}
	
	@MockBean
	private ReCaptchaService captchaService;

	@MockBean
	private RegistrationService registrationService;

	@MockBean
	private CaptchaSettings captchaSettings;

	@MockBean
	private Environment environment;

	@MockBean
	private EmailService emailService;

	@Autowired
	private RegistrationRestController restController;

	@Captor
	ArgumentCaptor<String[]> recepients;
	@Captor
	ArgumentCaptor<String> template;
	@Captor
	ArgumentCaptor<String> from;
	@Captor
	ArgumentCaptor<String> subject;
	@Captor
	ArgumentCaptor<String> dtoName;
	@Captor
	ArgumentCaptor<RegistrationToken> dto;
	@Captor
	ArgumentCaptor<Locale> locale;
	
	@BeforeEach
	public void setup() {
		Mockito.when(captchaSettings.getKey()).thenReturn("test captcha key");
		Mockito.when(registrationService.commitToken(Mockito.any(UserRegistrationDTO.class))).thenReturn(new RegistrationToken());
	}

	@Test
	public void testRegistrationDelete() throws Exception {
		final UserRegistrationCompleteDTO userDTO = new UserRegistrationCompleteDTO();
		this.restController.completeRegistration(userDTO);
		Mockito.verify(this.registrationService).completeRegistration(userDTO);
	}

	@Test
	public void testRegistrationGet() throws Exception {
		SnapshotListener.expect(this.restController.getMapping()).toMatchSnapshot();
	}

	@Test
	public void testRegistrationPost() throws Exception {
		final UserRegistrationDTO userDTO = new UserRegistrationDTO();
		this.restController.newUserRegistration(userDTO);

		Mockito.verify(this.registrationService).commitToken(userDTO);
		Mockito.verify(this.emailService).sendMessage(this.recepients.capture(), this.template.capture(),
				this.from.capture(), this.subject.capture(), this.dtoName.capture(), this.dto.capture(),
				this.locale.capture());

		SnapshotListener.expect(this.recepients.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.template.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.from.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.subject.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.dtoName.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.dto.getValue()).toMatchSnapshot();
		SnapshotListener.expect(this.locale.getValue()).toMatchSnapshot();
	}

}
