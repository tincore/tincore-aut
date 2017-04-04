package com.tincore.auth.server.form;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tincore.auth.server.domain.OauthClientDetails;
import com.tincore.auth.server.domain.User;

@Mapper(componentModel = "spring", uses = FormMapperUtil.class)
public interface FormMapper {

	UserForm toUserForm(User user);

	UserEditForm toUserEditForm(User user);

	@Mapping(source = "userAuthorities", target = "userAuthorities", ignore = true)
	@Mapping(source = "username", target = "username", ignore = true)
	void update(UserEditForm userForm, @MappingTarget User user);

	ClientForm toClientForm(OauthClientDetails oauthClientDetails);

	ClientCreateForm toClientCreateForm(OauthClientDetails oauthClientDetails);

	void update(ClientForm userForm, @MappingTarget OauthClientDetails oauthClientDetails);

	void update(ClientCreateForm userForm, @MappingTarget OauthClientDetails oauthClientDetails);
}
