package org.example.authentication.controller;

import org.example.authentication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class TestController {
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/hello")
	public ResponseEntity<String> helloWorld(@RequestHeader("Authorization") String token) {
		// Loại bỏ "Bearer " khỏi token
		String jwtToken = token.substring(7);

		// Xác thực token
		if (jwtUtil.validateToken(jwtToken, jwtUtil.extractUsername(jwtToken))) {
			return ResponseEntity.ok("Hello " + jwtUtil.extractUsername(jwtToken));
		} else {
			return ResponseEntity.status(401).body("Unauthorized");
		}
	}
}
