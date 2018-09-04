package com.bst.user.registration.components;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.components.EmailService;

public aspect UserRegistrationAspect {

	private Environment environment;
	private EmailService emailService;

	after() returning(RegistrationToken token) throws MessagingException: 
		call(* com.bst.user.registration.components.RegistrationService.commitToken(*)) {
		
		emailService.sendMessage(new String[] { token.getEmail() },
				"auth-signup-confirm", "automator@brainspeedtech.com", "Continue your registration", 
				token, Locale.ENGLISH);
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
