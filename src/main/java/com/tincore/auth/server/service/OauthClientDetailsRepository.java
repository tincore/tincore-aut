package com.tincore.auth.server.service;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tincore.auth.server.domain.OauthClientDetails;

public interface OauthClientDetailsRepository extends CrudRepository<OauthClientDetails, String> {
	Optional<OauthClientDetails> findByClientId(String clientId);
}
