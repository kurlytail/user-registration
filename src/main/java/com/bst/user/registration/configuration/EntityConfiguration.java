package com.bst.user.registration.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.bst.user.registration.entities")
@EnableJpaRepositories("com.bst.user.registration.repositories")
public class EntityConfiguration {

}
