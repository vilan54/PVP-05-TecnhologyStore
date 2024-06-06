package com.vilan.pablo.technology.store.rest.dtos;

import com.vilan.pablo.technology.store.model.entities.User;

public class UserConversor {
	
	private UserConversor() {}
	
	public final static UserDto toUserDto(User user) {
		return new UserDto(user.getEmail());
	}
	
	public final static User toUser(UserDto userDto) {
		
		return new User(userDto.getEmail(), userDto.getPassword());
	}
	
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		
		return new AuthenticatedUserDto(serviceToken, user.getEmail());
		
	}

}