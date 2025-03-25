package com.bankmanagementsystem.operations.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "User_Credentials")
@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 123456780, allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "user_seq")
	private long id;
	@Column(unique = true, nullable = false)
	private String name;
	private String username;
	private String password;
	public String getPassword() {
		return password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
