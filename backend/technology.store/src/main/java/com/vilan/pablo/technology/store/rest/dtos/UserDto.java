package com.vilan.pablo.technology.store.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {
	
	public interface AllValidations {}
	
	public interface UpdateValidations {}

	private String password;
	private String email;


	public UserDto() {}

	public UserDto(String email) {
		this.email = email.trim();
	}



	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	@Email(groups={AllValidations.class, UpdateValidations.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}


}

