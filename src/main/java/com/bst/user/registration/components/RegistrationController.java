package com.bst.user.registration.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/auth")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserRegistrationDTO userDto = new UserRegistrationDTO();
		model.addAttribute("user", userDto);
		return "auth-signup";
	}

	@RequestMapping(value = "/signup-confirm", method = RequestMethod.POST)
	public String registerUserAccount(@ModelAttribute("user") @Validated UserRegistrationDTO accountDto,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "auth-signup";
		}

		registrationService.commitToken(accountDto);

		return "auth-signup-confirm";
	}

	@RequestMapping(value = "/signup-continue", method = RequestMethod.GET)
	public String confirmUserAccount(@Validated UserConfirmationDTO confirmationDto, WebRequest request, Model model) {

		UserRegistrationCompleteDTO continueDto = new UserRegistrationCompleteDTO();
		continueDto.setEmail(confirmationDto.getEmail());
		continueDto.setHash(confirmationDto.getHash());

		model.addAttribute("user", continueDto);

		return "auth-signup-continue";
	}

	@RequestMapping(value = "/signup-complete", method = RequestMethod.POST)
	public String completeRegistration(@ModelAttribute("user") @Validated UserRegistrationCompleteDTO completeDto,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "auth-signup";
		}
		
		registrationService.completeRegistration(completeDto);

		return "auth-signin";
	}
}
