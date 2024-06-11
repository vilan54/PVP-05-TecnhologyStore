package com.vilan.pablo.technology.store.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

	public interface AllValidations {}
	
	public interface UpdateValidations {}

	private String id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String telephone;

	public UserDto() {}

	public UserDto(String id, String username, String email, String name, String surname, String telephone) {
		this.id = id;
		this.username = username != null ? username.trim() : null;
		this.email = email.trim();
		this.name = name.trim();
		this.surname = surname.trim();
		this.telephone = telephone.trim();
	}

	public String getId(){return this.id;}
	public void setId(String id){this.id = id;}

	@NotNull(message = "Usermame is mandatory")
	@Size(min=1, max=60, message = "Size must be between 1 and 60")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.trim();
	}

	@NotNull(message = "Password is mandatory")
	@Size(min=1, max=60, message = "Size must be between 1 and 60")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(message = "Name is mandatory")
	@Size(min=1, max=60, message = "Size must be between 1 and 60")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	@NotNull(message = "Surname is mandatory")
	@Size(min=1, max=60, message = "Size must be between 1 and 60")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String lastName) {
		this.surname = lastName.trim();
	}

	@NotNull(message = "Email is mandatory")
	@Size(min=1, max=60, message = "Size must be between 1 and 60")
	@Email(message = "Must be a valid email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	@NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Must be a valid telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}