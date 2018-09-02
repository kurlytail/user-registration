package com.bst.user.registration.components;

import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.bst.user.registration.entities.RegistrationToken;

public aspect UserRegistrationAspect {

	private MailSender mailSender;
	private Environment environment;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	after() returning(RegistrationToken token): 
		call(* com.bst.user.registration.components.RegistrationService.commitToken(*)) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(token.getEmail());
		message.setFrom("automator@brainspeedtech.com");
		message.setSubject("Continue your registration");
		message.setText("http://localhost:" + environment.getProperty("local.server.port")
				+ "/auth/signup-continue?user=" + token.getEmail() + "&hash=" + token.getHash());
		
		mailSender.send(message);
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}
