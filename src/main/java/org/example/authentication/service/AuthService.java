package org.example.authentication.service;

import org.example.authentication.entities.User;

public interface AuthService {
	User register(String username, String password);
}
