package org.example.authentication.controller;

import org.example.authentication.dto.request.AuthRequest;
import org.example.authentication.dto.request.RegisterRequest;
import org.example.authentication.service.impl.AuthServiceImp;
import org.example.authentication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthServiceImp authServiceImp;
	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil;

	private UserDetailsService userDetailsService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, AuthServiceImp authServiceImp) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.authServiceImp = authServiceImp;
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) {
		authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
		);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		return jwtUtil.generateToken(userDetails.getUsername());
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		authServiceImp.register(request.getUsername(), request.getPassword());
		return ResponseEntity.ok("User registered successfully");
	}
}
