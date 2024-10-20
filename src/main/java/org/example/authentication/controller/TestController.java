package org.example.authentication.controller;

import org.example.authentication.entities.Role;
import org.example.authentication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class TestController {
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/user")
	public ResponseEntity<String> helloWorld(@RequestHeader("Authorization") String token) {
		String jwtToken = token.substring(7);

		if (jwtUtil.validateToken(jwtToken, jwtUtil.extractUsername(jwtToken))) {
			// Lấy vai trò từ token
			List<String> roles = jwtUtil.extractRoles(jwtToken);
			if (roles.contains(Role.USER.toString())) {
				return ResponseEntity.ok("Hello User: " + jwtUtil.extractUsername(jwtToken));
			} else {
				return ResponseEntity.status(403).body("Forbidden: Insufficient permissions");
			}
		} else {
			return ResponseEntity.status(401).body("Unauthorized");
		}
	}

	@GetMapping("/admin")
	public ResponseEntity<String> helloAdmin(@RequestHeader("Authorization") String token) {
		String jwtToken = token.substring(7);

		if (jwtUtil.validateToken(jwtToken, jwtUtil.extractUsername(jwtToken))) {
			// Lấy vai trò từ token
			List<String> roles = jwtUtil.extractRoles(jwtToken);
			if (roles.contains(Role.ADMIN.toString())) {
				return ResponseEntity.ok("Hello ADMIN: " + jwtUtil.extractUsername(jwtToken));
			} else {
				return ResponseEntity.status(403).body("Forbidden: Insufficient permissions");
			}
		} else {
			return ResponseEntity.status(401).body("Unauthorized");
		}
	}
}
