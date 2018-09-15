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
    private String reReCaptchaResponse;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReCaptchaResponse() {
		return reReCaptchaResponse;
	}

	public void setReCaptchaResponse(String reReCaptchaResponse) {
		this.reReCaptchaResponse = reReCaptchaResponse;
	}

}
