package com.bst.user.registration.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bst.utility.constraints.ValidReCaptcha;

public class UserRegistrationDTO {

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@ValidReCaptcha
	private String reCaptchaResponse;

	private String captchaKey;

	public String getCaptchaKey() {
		return this.captchaKey;
	}

	public String getEmail() {
		return this.email;
	}

	public String getReCaptchaResponse() {
		return this.reCaptchaResponse;
	}

	public void setCaptchaKey(final String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setReCaptchaResponse(final String reReCaptchaResponse) {
		this.reCaptchaResponse = reReCaptchaResponse;
	}

	@Override
	public String toString() {
		return "UserRegistrationDTO [email=" + this.email + ", reReCaptchaResponse=" + this.reCaptchaResponse + "]";
	}

}
