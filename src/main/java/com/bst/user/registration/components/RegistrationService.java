package com.bst.user.registration.components;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bst.user.authentication.components.UserService;
import com.bst.user.authentication.entities.User;
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
	public RegistrationToken commitToken(UserRegistrationDTO accountDto) {

		RegistrationToken oldToken = tokenRepository.findByEmail(accountDto.getEmail());
		if (oldToken != null) {
			tokenRepository.delete(oldToken);
			entityManager.flush();
		}

		RegistrationToken token = new RegistrationToken(accountDto.getEmail());
		tokenRepository.save(token);
		entityManager.flush();
		entityManager.refresh(token);

		return token;
	}

	@Transactional
	public User completeRegistration(UserRegistrationCompleteDTO completeDto) {
		RegistrationToken token = tokenRepository.findByEmail(completeDto.getEmail());
		if (token == null) {
			return null;
		}

		User newUser = userService.createUser(completeDto.getEmail(), completeDto.getName(), completeDto.getPassword());
		tokenRepository.delete(token);
		return newUser;
	}
}
