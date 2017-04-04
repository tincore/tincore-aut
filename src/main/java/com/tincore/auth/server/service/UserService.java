package com.tincore.auth.server.service;

import java.util.List;
import java.util.Optional;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserCreateForm;

public interface UserService {
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";

	public static final String[] ROLES_USER = { ROLE_USER };

	void createDefaultEntitities();

	void delete(String username, Optional<User> principalUser);

	User createUser(UserCreateForm userRegisterForm);

	User getUser(String username);

	User updateUser(User user, String newPassword);

	User createUserIfNotExists(String username, String password, String... roles);

	List<User> findAll();

	Optional<User> findByUsername(String username);

}
