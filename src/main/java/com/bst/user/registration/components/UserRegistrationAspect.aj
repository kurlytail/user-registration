package com.bst.user.registration.components;

import java.util.Locale;

import org.springframework.core.env.Environment;

import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.components.EmailService;

public aspect UserRegistrationAspect {

	private Environment environment;
	private EmailService emailService;

	after() returning(RegistrationToken token): 
		call(* com.bst.user.registration.components.RegistrationService.commitToken(*)) {
		
		try {
			emailService.sendMessage(new String[] { token.getEmail() },
					"email/auth-signup-confirm", "automator@brainspeedtech.com", "Continue your registration", 
					"user", token, Locale.ENGLISH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
