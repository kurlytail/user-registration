package com.bst.user.registration.components;

import org.springframework.mail.javamail.JavaMailSender;

import com.bst.user.registration.entities.RegistrationToken;

public aspect UserRegistrationAspect {
	
	private JavaMailSender javaMailSender;
	
	after() returning(RegistrationToken token): 
		call(* com.bst.user.registration.components.RegistrationController.commitToken(*)) {
	    System.out.println("Caught!!!!" + token.toString());
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
}
