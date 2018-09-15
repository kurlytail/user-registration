package com.bst.user.registration.validators;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bst.user.registration.constraints.ValidConfirmationHash;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.user.registration.repositories.RegistrationTokenRepository;

public class ConfirmationHashValidator implements ConstraintValidator<ValidConfirmationHash, Object> {

	@Autowired
	private RegistrationTokenRepository tokenRepository;

	@Override
	public void initialize(ValidConfirmationHash constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object dto, ConstraintValidatorContext context) {
		
		String dtoEmail;
		String dtoHash;
		
		try {
			Method getEmailMethod = dto.getClass().getMethod("getEmail");
			Method getHashMethod = dto.getClass().getMethod("getHash");
			
			dtoEmail = (String) getEmailMethod.invoke(dto);
			dtoHash = (String) getHashMethod.invoke(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		RegistrationToken token = tokenRepository.findByEmail(dtoEmail);
		if (token == null) return false;
		return token.getHash().equals(dtoHash);
	}

}