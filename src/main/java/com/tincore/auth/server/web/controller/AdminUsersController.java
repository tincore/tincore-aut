package com.tincore.auth.server.web.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.FormMapper;
import com.tincore.auth.server.form.UserCreateForm;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.service.UserInvalidException;
import com.tincore.auth.server.service.UserRepository;
import com.tincore.auth.server.service.UserService;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUsersController {

	@Autowired
	private FormMapper formMapper;

	@Autowired
	private UserService userService;

	@GetMapping
	public String doUsersDisplay(Map<String, Object> model) {
		model.put("users", userService.findAll());
		UserCreateForm userCreateForm = new UserCreateForm();
		model.put("user", userCreateForm);
		return "users";
	}

	@PostMapping
	public String doUserCreate(@ModelAttribute("user") @Valid UserCreateForm userCreateForm, BindingResult bindingResult,
			Map<String, Object> model) {
		try {
			if (bindingResult.hasErrors()) {
				model.put("users", userService.findAll());
				model.put("user", userCreateForm);
				model.put("message", "Error creating. Validation");
				return "users";
			}

			User user = userService.createUser(userCreateForm);
			model.put("message", "Success creating " + user.getUsername());
			return "redirect:/admin/users";
		} catch (UserInvalidException e) {
			model.put("user", userCreateForm);
			model.put("message", String.format("Error creating %s. User exists", userCreateForm.getUsername()));
			return "users";
		}

	}

	@GetMapping("/{username}")
	public String doUserDisplay(@PathVariable(value = "username") String username, Map<String, Object> model,
			Principal principal) {
		model.put("user", formMapper.toUserEditForm(
				userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username))));
		return "user";
	}

	@PostMapping("/{username}")
	public String doUserEdit(@PathVariable(value = "username") String username,
			@ModelAttribute("user") @Valid UserEditForm userEditForm, BindingResult bindingResult, @RequestParam("action") String action, Map<String, Object> model) {
		if ("cancel".equals(action)){
			return "redirect:/admin/users";
		}
		
		if (bindingResult.hasErrors()) {
			model.put("user", userEditForm);
			model.put("message", "Error creating. Validation");
			return "user";
		}

		User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		formMapper.update(userEditForm, user);
		userService.updateUser(user, userEditForm.getNewPassword());
		return "redirect:/admin/users";
	}

	@PostMapping("/{username}/delete")
	public String doDelete(@PathVariable(value = "username") String username,
			@AuthenticationPrincipal User principalUser, Map<String, Object> model) {
		userService.delete(username, Optional.of(principalUser));
		model.put("message", String.format("User %s was successfully deleted", username));
		return "redirect:/admin/users";
	}

}