package org.example.authentication.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "booklib_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;

}
