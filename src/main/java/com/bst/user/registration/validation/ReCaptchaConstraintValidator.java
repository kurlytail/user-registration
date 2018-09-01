package com.bst.user.registration.validation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bst.user.registration.components.ReCaptchaService;
import com.bst.user.registration.constraints.ValidReCaptcha;

public class ReCaptchaConstraintValidator implements ConstraintValidator<ValidReCaptcha, String> {

    @Autowired
    private ReCaptchaService reCaptchaService;

    @Override
    public void initialize(ValidReCaptcha constraintAnnotation) {

    }

    @Override
    public boolean isValid(String reCaptchaResponse, ConstraintValidatorContext context) {

        if (reCaptchaResponse == null || reCaptchaResponse.isEmpty()){
            return true;
        }

        return reCaptchaService.validate(reCaptchaResponse);
    }

}