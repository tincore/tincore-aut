package com.tincore.auth.server.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserCreateForm;

// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=test -dpassword=test
// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=clientcredentials

@Service
@Primary
@Qualifier("tincoreUserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Value("${debug}")
	private boolean debug;

	@Override
	@Transactional
	public void createDefaultEntitities() {
		createUserIfNotExists("gliderun", "gliderun", ROLE_CLIENT);
		createUserIfNotExists("gbm", "gbm", ROLE_CLIENT);
		createUserIfNotExists("gliderun_web", "gliderun", ROLE_CLIENT);

		createUserIfNotExists("admin", "admin", ROLE_ADMIN, ROLE_USER);
		if (debug) {
			createUserIfNotExists("user", "user", ROLES_USER);
		}
	}

	@Override
	public User createUser(UserCreateForm userCreateForm) {
		return createUser(userCreateForm, ROLES_USER);
	}

	public User createUser(UserCreateForm userCreateForm, String... roles) {
		userRepository.findByUsername(userCreateForm.getUsername()).ifPresent(user -> {
			throw new UserInvalidException(userCreateForm.getUsername());
		});

		User user = new User(userCreateForm.getUsername(), passwordEncoder.encode(userCreateForm.getPassword()),
				roles);
		user.setFirstName(userCreateForm.getFirstName());
		user.setLastName(userCreateForm.getLastName());

		return userRepository.save(user);
	}

	@Override
	public User createUserIfNotExists(String username, String password, String... roles) {
		return userRepository.findByUsername(username).orElseGet(() -> {
			User user = createUser(new UserCreateForm(username, password), roles);
			return user;
		});
	}

	@Override
	@Transactional
	public void delete(String username, Optional<User> principalUser) {
		if (principalUser.isPresent() && principalUser.get().getUsername().equals(username)) {
			throw new UserInvalidException(username);
		}
		userRepository.findByUsername(username).ifPresent(user -> userRepository.delete(user));
	}

	@Override
	public User getUser(String username) {
		return findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	public boolean isRolePresent(User user, String role) {
		return user.getUserAuthorities() != null && user.getUserAuthorities().stream()
				.filter(a -> a.getAuthority().equals(role)).findFirst().isPresent();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		Hibernate.initialize(user.getAuthorities());
		return user;
	}

	@Override
	public User updateUser(User user, String newPassword) {
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}

		return userRepository.save(user);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	
}