package com.bst.user.registration.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bst.user.registration.validators.ConfirmationHashValidator;

@Documented
@Constraint(validatedBy = ConfirmationHashValidator.class)
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidConfirmationHash {

	Class<?>[] groups() default {};

	String message() default "Invalid Credentials";

	Class<? extends Payload>[] payload() default {};
}