package com.tincore.auth.server.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 200)
	private String clientId;

	@NotNull
	@Size(min = 2, max = 200)
	private String clientSecret;

	private String resourceIds;

	private String scope;

	private String authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	private String autoapprove;

	@NotNull
	private int accessTokenValidity;

	@NotNull
	private int refreshTokenValidity;

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