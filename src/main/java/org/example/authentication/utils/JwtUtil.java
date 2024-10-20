package org.example.authentication.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "your_secret_key"; // Thay thế bằng khóa bí mật của bạn
	private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	public String generateToken(String username, List<String> roles) {
		return Jwts.builder()
						.setSubject(username)
						.claim("roles", roles) // Thêm vai trò vào token
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
						.compact();
	}

	public boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	public List extractRoles(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("roles", List.class); // Lấy danh sách vai trò từ token
	}
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
