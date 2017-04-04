package com.tincore.auth.server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tincore.auth.server.domain.OauthClientDetails;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private UserService userService;

	@Autowired
	private OauthClientDetailsRepository oauthClientDetailsRepository;

	@Value("${debug}")
	private boolean debug;

	@Override
	public OauthClientDetails getDefaultOauthClientDetails() {
		OauthClientDetails oauthClientDetails = new OauthClientDetails();
		oauthClientDetails.setScope("read,write,openid");
		oauthClientDetails.setAuthorizedGrantTypes("authorization_code,refresh_token,password,implicit");
		oauthClientDetails.setAutoapprove("read,write,openid");
		oauthClientDetails.setAccessTokenValidity(2147483647);
		oauthClientDetails.setRefreshTokenValidity(2147483647);

		return oauthClientDetails;
	}

	@Override
	public void delete(OauthClientDetails oauthClientDetails) {
		userService.delete(oauthClientDetails.getClientId(), Optional.empty());
		oauthClientDetailsRepository.delete(oauthClientDetails);
	}

	@Override
	public Optional<OauthClientDetails> findByClientId(String clientId) {
		return oauthClientDetailsRepository.findByClientId(clientId);
	}

	@Override
	public OauthClientDetails save(OauthClientDetails oauthClientDetails) {
		userService.createUserIfNotExists(oauthClientDetails.getClientId(), oauthClientDetails.getClientSecret(),
				UserService.ROLE_CLIENT);
		return oauthClientDetailsRepository.save(oauthClientDetails);
	}

	@Override
	public Iterable<OauthClientDetails> findAll() {
		return oauthClientDetailsRepository.findAll();
	}

}