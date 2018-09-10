package com.bst.user.registration.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bst.user.registration.dto.UserConfirmationDTO;
import com.bst.user.registration.dto.UserRegistrationCompleteDTO;
import com.bst.user.registration.dto.UserRegistrationDTO;

@Controller
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@Value("${bst.template.user.registration.signup:auth-signup}")
	private String authSignupTemplate;
	
	@Value("${bst.template.user.registration.signupConfirm:auth-signup-confirm}")
	private String authSignupConfirmTemplate;
	
	@Value("${bst.template.user.registration.signupContinue:auth-signup-continue}")
	private String authSignupContinueTemplate;

	@Value("${bst.template.user.registration.signin:auth-signin}")
	private String authSigninTemplate;

	@RequestMapping(value = "${bst.uri.user.registration.signup:/auth/signup}", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserRegistrationDTO userDto = new UserRegistrationDTO();
		model.addAttribute("user", userDto);
		return authSignupTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signupConfirm:/auth/signup-confirm}", method = RequestMethod.POST)
	public String registerUserAccount(@ModelAttribute("user") @Validated UserRegistrationDTO accountDto,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return authSignupTemplate;
		}

		registrationService.commitToken(accountDto);

		return authSignupConfirmTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signupContinue:/auth/signup-continue}", method = RequestMethod.GET)
	public String confirmUserAccount(@Validated UserConfirmationDTO confirmationDto, WebRequest request, Model model) {

		UserRegistrationCompleteDTO continueDto = new UserRegistrationCompleteDTO();
		continueDto.setEmail(confirmationDto.getEmail());
		continueDto.setHash(confirmationDto.getHash());

		model.addAttribute("user", continueDto);

		return authSignupContinueTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signupComplete:/auth/signup-complete}", method = RequestMethod.POST)
	public String completeRegistration(@ModelAttribute("user") @Validated UserRegistrationCompleteDTO completeDto,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return authSignupTemplate;
		}

		registrationService.completeRegistration(completeDto);

		return authSigninTemplate;
	}
}
