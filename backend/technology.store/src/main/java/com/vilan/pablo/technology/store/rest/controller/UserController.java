package com.vilan.pablo.technology.store.rest.controller;

import java.net.URI;

import javax.management.InstanceNotFoundException;

import static com.vilan.pablo.technology.store.rest.dtos.UserConversor.toUser;
import static com.vilan.pablo.technology.store.rest.dtos.UserConversor.toAuthenticatedUserDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;

import com.google.firebase.auth.FirebaseAuthException;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.DuplicateInstanceException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectPasswordException;
import com.vilan.pablo.technology.store.model.service.UserService;
import com.vilan.pablo.technology.store.rest.dtos.AuthenticatedUserDto;
import com.vilan.pablo.technology.store.rest.dtos.ErrorsDto;
import com.vilan.pablo.technology.store.rest.dtos.LoginParamsDto;
import com.vilan.pablo.technology.store.rest.dtos.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
	private UserService userService;

    @ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception) {
		
		String errorMessage = "Error: Incorrect Login";

		return new ErrorsDto(errorMessage);
		
	}

    @ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectPasswordException(IncorrectPasswordException exception) {
		
		String errorMessage = "Error: Incorrect Password Exception";

		return new ErrorsDto(errorMessage);
		
	}

    @PostMapping("/signUp")
	public ResponseEntity<AuthenticatedUserDto> signUp(
		@Validated({UserDto.AllValidations.class}) @RequestBody UserDto userDto) throws DuplicateInstanceException, InstanceNotFoundException, FirebaseAuthException, IncorrectLoginException {
		
		User user = toUser(userDto);
		
		String token = userService.signUp(user);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(user.getEmail()).toUri();   
	
		return ResponseEntity.created(location).body(toAuthenticatedUserDto(token, user));

	}

    @PostMapping("/login")
	public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params)
	    throws IncorrectLoginException {
		
		String token = userService.login(params.getUserName(), params.getPassword());
		
        User user = new User(params.getUserName(), params.getPassword());

		return toAuthenticatedUserDto(token, user);
		
	}

}
