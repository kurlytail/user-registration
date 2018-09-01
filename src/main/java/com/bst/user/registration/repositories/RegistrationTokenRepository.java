package com.bst.user.registration.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bst.user.registration.entities.RegistrationToken;

@Repository
public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, Long>{
	public RegistrationToken findByEmail(String email);
}
