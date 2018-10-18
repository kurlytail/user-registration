package com.bst.user.registration.components;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bst.user.authentication.components.UserService;
import com.bst.user.authentication.entities.Person;
import com.bst.user.registration.dto.UserRegistrationCompleteDTO;
import com.bst.user.registration.dto.UserRegistrationDTO;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.user.registration.repositories.RegistrationTokenRepository;

@Service
public class RegistrationService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RegistrationTokenRepository tokenRepository;

	@Autowired
	private UserService userService;

	@Transactional
	public RegistrationToken commitToken(final UserRegistrationDTO accountDto) {

		final RegistrationToken oldToken = this.tokenRepository.findByEmail(accountDto.getEmail());
		if (oldToken != null) {
			this.tokenRepository.delete(oldToken);
			this.entityManager.flush();
		}

		final RegistrationToken token = new RegistrationToken(accountDto.getEmail());
		this.tokenRepository.save(token);
		this.entityManager.flush();
		this.entityManager.refresh(token);

		return token;
	}

	@Transactional
	public Person completeRegistration(final UserRegistrationCompleteDTO completeDto) {
		final RegistrationToken token = this.tokenRepository.findByEmail(completeDto.getEmail());
		if (token == null) {
			return null;
		}

		final Person newUser = this.userService.createUser(completeDto.getEmail(), completeDto.getName(),
				completeDto.getPassword());
		this.tokenRepository.delete(token);
		return newUser;
	}
}
