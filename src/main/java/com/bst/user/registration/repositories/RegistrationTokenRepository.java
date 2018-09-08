package com.bst.user.registration.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bst.user.registration.entities.RegistrationToken;

@RepositoryRestResource
public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, Long>{
	public RegistrationToken findByEmail(String email);
}
