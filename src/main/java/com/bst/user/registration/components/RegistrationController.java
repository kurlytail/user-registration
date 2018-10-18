package com.bst.user.registration.components;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.services.EmailService;

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

	@Autowired
	private Environment environment;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "${bst.uri.user.registration.signupComplete:/auth/signup-complete}", method = RequestMethod.POST)
	public String completeRegistration(@ModelAttribute("user") @Validated final UserRegistrationCompleteDTO completeDto,
			final BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return this.authSignupTemplate;
		}

		this.registrationService.completeRegistration(completeDto);

		return this.authSigninTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signupContinue:/auth/signup-continue}", method = RequestMethod.GET)
	public String confirmUserAccount(@Validated final UserConfirmationDTO confirmationDto, final WebRequest request,
			final Model model) {

		final UserRegistrationCompleteDTO continueDto = new UserRegistrationCompleteDTO();
		continueDto.setEmail(confirmationDto.getEmail());
		continueDto.setHash(confirmationDto.getHash());

		model.addAttribute("user", continueDto);

		return this.authSignupContinueTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signupConfirm:/auth/signup-confirm}", method = RequestMethod.POST)
	public String registerUserAccount(@ModelAttribute("user") @Validated final UserRegistrationDTO accountDto,
			final BindingResult result, final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return this.authSignupTemplate;
		}

		final RegistrationToken token = this.registrationService.commitToken(accountDto);

		try {
			this.emailService.sendMessage(new String[] { token.getEmail() },
					this.environment.getProperty("bst.email.template.user.registration.signupConfirm",
							"email/auth-signup-confirm"),
					this.environment.getProperty("bst.email.from", "automator@brainspeedtech.com"),
					"Continue your registration", "user", token, Locale.ENGLISH);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return this.authSignupConfirmTemplate;
	}

	@RequestMapping(value = "${bst.uri.user.registration.signup:/auth/signup}", method = RequestMethod.GET)
	public String showRegistrationForm(final WebRequest request, final Model model) {
		final UserRegistrationDTO userDto = new UserRegistrationDTO();
		model.addAttribute("user", userDto);
		return this.authSignupTemplate;
	}
}
