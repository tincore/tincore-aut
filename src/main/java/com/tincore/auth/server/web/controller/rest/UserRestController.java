package com.tincore.auth.server.web.controller.rest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.auth.server.domain.User;

@RestController
@RequestMapping({ "/user", "/me" })
public class UserRestController {

	@GetMapping
	public Map<String, String> user(@AuthenticationPrincipal User principalUser) {
		Map<String, String> map = new LinkedHashMap<>();
		if (principalUser != null) {
			map.put("name", principalUser.getUsername());
			if (principalUser.getUserAuthorities() != null) {
				String commaSeparatedAuthorities = principalUser.getUserAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(", "));
				map.put("authorities", commaSeparatedAuthorities);
			}
		}
		return map;
	}
}