package com.tincore.auth.server.service;

import java.util.Optional;

import com.tincore.auth.server.domain.OauthClientDetails;

public interface ClientService {

	OauthClientDetails getDefaultOauthClientDetails();

	void delete(OauthClientDetails client);

	Optional<OauthClientDetails> findByClientId(String clientId);

	OauthClientDetails save(OauthClientDetails oauthClientDetails);

	Iterable<OauthClientDetails> findAll();

}
