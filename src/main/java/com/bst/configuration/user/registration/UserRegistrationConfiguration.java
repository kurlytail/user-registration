package com.bst.configuration.user.registration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.bst.user.registration.components.RegistrationRestController;
import com.bst.user.registration.configuration.RestTemplateConfig;

@Configuration
@ComponentScan(basePackageClasses = { RegistrationRestController.class, RestTemplateConfig.class })
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class UserRegistrationConfiguration {
}
