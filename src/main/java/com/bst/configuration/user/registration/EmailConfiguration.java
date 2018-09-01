package com.bst.configuration.user.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
	
	@Bean(name="javaMailSender")
	public JavaMailSender createJavaMailSender() {
		return new JavaMailSenderImpl();
	}
}
