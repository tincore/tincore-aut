package com.tincore.auth.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OauthClientDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String clientId;

	@Column
	private String resourceIds;

	@Column
	private String clientSecret;

	@Column
	private String scope;

	@Column
	private String authorizedGrantTypes;

	@Column
	private String webServerRedirectUri;

	@Column
	private String authorities;

	@Column
	private String autoapprove;

	@Column
	private int accessTokenValidity;

	@Column
	private int refreshTokenValidity;

	@Column
	private String additionalInformation;

	public int getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public String getAuthorities() {
		return authorities;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public String getAutoapprove() {
		return autoapprove;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public int getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public String getScope() {
		return scope;
	}

	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	public void setAccessTokenValidity(int accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setRefreshTokenValidity(int refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}

}
