package com.bst.configuration.user.registration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({"com.bst.user.registration.components", "com.bst.user.registration.configuration"})
@ImportResource({"classpath:Aspect.xml"})
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class UserRegistrationConfiguration {
}
