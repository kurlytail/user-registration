package com.bst.user.registration.components;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bst.user.registration.dto.UserRegistrationCompleteDTO;
import com.bst.user.registration.dto.UserRegistrationDTO;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.configuration.CaptchaSettings;
import com.bst.utility.services.EmailService;

@RestController
@RequestMapping("registration")
public class RegistrationRestController {

	@Autowired
	private CaptchaSettings captchaSettings;

	@Autowired
	private EmailService emailService;

	@Autowired
	private Environment environment;

	@Autowired
	private RegistrationService registrationService;

	@DeleteMapping
	public void completeRegistration(final @Validated @RequestBody UserRegistrationCompleteDTO userDTO) {
		this.registrationService.completeRegistration(userDTO);
	}
	
	@GetMapping
	public UserRegistrationDTO getMapping() {
		final UserRegistrationDTO userDTO = new UserRegistrationDTO();
		userDTO.setCaptchaKey(this.captchaSettings.getKey());
		return userDTO;
	}

	@PostMapping
	public void newUserRegistration(final @Validated @RequestBody UserRegistrationDTO userDTO) throws Exception {
		final RegistrationToken token = this.registrationService.commitToken(userDTO);
		this.emailService.sendMessage(new String[] { token.getEmail() },
				this.environment.getProperty("bst.email.template.user.registration.signupConfirm",
						"email/auth-signup-confirm"),
				this.environment.getProperty("bst.email.from", "automator@brainspeedtech.com"),
				"Continue your registration", "user", token, Locale.ENGLISH);
	}
}
