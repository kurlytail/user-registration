package com.bst.user.registration.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bst.user.registration.entities.RegistrationToken;

@PreAuthorize("hasRole('ADMIN')")
public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, Long> {
	@Override
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public void delete(RegistrationToken entity);

	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public RegistrationToken findByEmail(String email);

	@Override
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public <S extends RegistrationToken> S save(S s);
}
