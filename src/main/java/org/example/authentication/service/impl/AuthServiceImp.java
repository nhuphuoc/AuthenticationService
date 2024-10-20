package org.example.authentication.service.impl;

import org.example.authentication.entities.User;
import org.example.authentication.repository.UserRepository;
import org.example.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImp implements AuthService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	@Autowired
	public AuthServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public User register(String username, String password) {
		if (userRepository.findByUsername(username) != null) {
			throw new RuntimeException("Username is already taken");
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRoles(Collections.singletonList("USER")); // Gán vai trò mặc định

		return userRepository.save(user);
	}
}
