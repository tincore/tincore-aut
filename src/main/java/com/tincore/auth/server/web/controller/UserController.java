package com.tincore.auth.server.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserCreateForm;
import com.tincore.auth.server.service.UserInvalidException;
import com.tincore.auth.server.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String doUserRegisterDisplay(Map<String, Object> model) {
		UserCreateForm userCreateForm = new UserCreateForm();
		model.put("user", userCreateForm);
		return "user_register";
	}

	@PostMapping("/register")
	public String doUserRegister(@ModelAttribute("user") @Valid UserCreateForm userCreateForm, BindingResult bindingResult, Map<String, Object> model) {
		if (bindingResult.hasErrors()) {
			model.put("user", userCreateForm);
			model.put("message", "Error creating. Validation");
			return "user_register";
		}

		try {
			User user = userService.createUser(userCreateForm);
			model.put("message", String.format("Success creating %s", user.getUsername()));
			return "redirect:/login";
		} catch (UserInvalidException e) {
			model.put("user", userCreateForm);
			model.put("message", String.format("Error creating %s. User exists", userCreateForm.getUsername()));
			return "user_register";
		}
	}

}