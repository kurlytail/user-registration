
package com.bst.user.registration.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bst.user.registration.constraints.ValidConfirmationHash;

@ValidConfirmationHash
public class UserConfirmationDTO {

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String hash;

	public String getEmail() {
		return this.email;
	}

	public String getHash() {
		return this.hash;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

}
