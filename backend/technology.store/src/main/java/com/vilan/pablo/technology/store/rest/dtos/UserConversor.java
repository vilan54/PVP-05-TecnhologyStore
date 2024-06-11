package com.vilan.pablo.technology.store.rest.dtos;

import com.vilan.pablo.technology.store.model.entities.User;

public class UserConversor {
	
	private UserConversor() {}
	
	public final static UserDto toUserDto(User user, String id) {
		return new UserDto(id, user.getUsername(), user.getEmail(), user.getName(), user.getSurname(), user.getTelephone());
	}
	
	public final static User toUser(UserDto userDto) {
		
		return new User(userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname(), userDto.getTelephone());
	}
	
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, UserDto userDto) {
		
		return new AuthenticatedUserDto(serviceToken, userDto);
		
	}

}