package com.bst.user.registration.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bst.user.registration.constraints.ValidConfirmationHash;

@ValidConfirmationHash
public class UserRegistrationCompleteDTO {

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String confirmPassword;

	@NotNull
	@NotEmpty
	private String hash;

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public String getEmail() {
		return this.email;
	}

	public String getHash() {
		return this.hash;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
