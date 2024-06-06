package com.vilan.pablo.technology.store.rest.dtos;


public class AuthenticatedUserDto {
	
	private String token;
	private String email;

	public AuthenticatedUserDto() {}
	
	public AuthenticatedUserDto(String token, String email) {
		
		this.token = token;
		this.email = email;
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setUserDto(String email) {
		this.email = email;
	}

}
