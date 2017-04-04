package com.tincore.auth.server.web.controller;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tincore.auth.server.domain.OauthClientDetails;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.ClientCreateForm;
import com.tincore.auth.server.form.ClientForm;
import com.tincore.auth.server.form.FormMapper;
import com.tincore.auth.server.service.ClientService;
import com.tincore.auth.server.service.EntityNotFoundException;

@Controller
@RequestMapping("/admin/clients")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminClientsController {

	@Autowired
	private FormMapper formMapper;

	@Autowired
	private ClientService clientService;

	@GetMapping
	public String doClientsDisplay(Map<String, Object> model) {
		model.put("clients", clientService.findAll());
		ClientCreateForm clientForm = formMapper.toClientCreateForm(clientService.getDefaultOauthClientDetails());
		model.put("client", clientForm);
		return "clients";
	}

	@PostMapping
	public String doClientCreate(@ModelAttribute("client") @Valid ClientCreateForm clientForm, BindingResult bindingResult,
			Map<String, Object> model) {
		if (bindingResult.hasErrors()) {
			model.put("clients", clientService.findAll());
			model.put("client", clientForm);
			model.put("message", "Error creating. Validation");
			return "clients";
		}

		OauthClientDetails oauthClientDetails = clientService.getDefaultOauthClientDetails();
		formMapper.update(clientForm, oauthClientDetails);
		clientService.save(oauthClientDetails);

		model.put("message", "Success creating " + clientForm.getClientId());
		return "redirect:/admin/clients";
	}

	@GetMapping("/{clientId}")
	public String doClientDisplay(@PathVariable(value = "clientId") String clientId, Map<String, Object> model,
			Principal principal) {
		OauthClientDetails oauthClientDetails = clientService.findByClientId(clientId)
				.orElseThrow(() -> new EntityNotFoundException(clientId));
		model.put("client", formMapper.toClientForm(oauthClientDetails));
		return "client";
	}

	@PostMapping("/{clientId}")
	public String doClientEdit(@PathVariable(value = "clientId") String clientId,
			@ModelAttribute("client") @Valid ClientForm clientForm, BindingResult bindingResult, @RequestParam("action") String action, Map<String, Object> model) {
		if ("cancel".equals(action)){
			return "redirect:/admin/clients";
		}
		
		if (bindingResult.hasErrors()) {
			model.put("client", clientForm);
			model.put("message", "Error creating. Validation");
			return "client";
		}

		
		OauthClientDetails oauthClientDetails = clientService.findByClientId(clientId)
				.orElseThrow(() -> new EntityNotFoundException(clientId));

		formMapper.update(clientForm, oauthClientDetails);
		clientService.save(oauthClientDetails);

		return "redirect:/admin/clients";
	}

	@PostMapping("/{clientId}/delete")
	public String doClientDelete(@PathVariable(value = "clientId") String clientId,
			@AuthenticationPrincipal User principalUser, Map<String, Object> model) {
		OauthClientDetails oauthClientDetails = clientService.findByClientId(clientId)
				.orElseThrow(() -> new EntityNotFoundException(clientId));
		clientService.delete(oauthClientDetails);
		model.put("message", String.format("Client %s was successfully deleted", clientId));
		return "redirect:/admin/clients";
	}

}