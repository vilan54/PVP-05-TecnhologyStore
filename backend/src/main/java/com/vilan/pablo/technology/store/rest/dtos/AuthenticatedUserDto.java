package com.vilan.pablo.technology.store.rest.dtos;


public class AuthenticatedUserDto {
	
	private String token;
	private String id;
	private String email;
	private String username;
	private String name;
	private String surname;
	private String telephone;

	public AuthenticatedUserDto() {}
	
	public AuthenticatedUserDto(String token, UserDto userDto) {
		this.token = token;
		this.id = userDto.getId();
		this.email = userDto.getEmail();
		this.username = userDto.getUsername();
		this.name = userDto.getName();
		this.surname = userDto.getSurname();
		this.telephone = userDto.getTelephone();
	}

	public String getToken() {return this.token;}
	public void setToken(String token) {this.token = token;}

	public String getId() {return this.id;}
	public void setId(String id) {this.id = id;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}

	public String getName(){return this.name;}
	public void setName(String name){this.name = name;}

	public String getSurname(){return this.surname;}
	public void setSurname(String surname){this.surname = surname;}

	public String getTelephone(){return this.telephone;}
	public void setTelephone(String telephone){this.telephone = telephone;}

}
