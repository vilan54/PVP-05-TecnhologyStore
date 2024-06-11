package com.vilan.pablo.technology.store.rest.controller;

import static com.vilan.pablo.technology.store.rest.dtos.UserConversor.toUser;
import static com.vilan.pablo.technology.store.rest.dtos.UserConversor.toAuthenticatedUserDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.PermissionException;
import com.vilan.pablo.technology.store.model.service.PermissionChecker;
import com.vilan.pablo.technology.store.model.service.UserService;
import com.vilan.pablo.technology.store.rest.dtos.AuthenticatedUserDto;
import com.vilan.pablo.technology.store.rest.dtos.ChangePasswordParamsDto;
import com.vilan.pablo.technology.store.rest.dtos.LoginParamsDto;
import com.vilan.pablo.technology.store.rest.dtos.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
	private UserService userService;

	@Autowired
	private PermissionChecker permissionChecker;

    @PostMapping("/signUp")
	public ResponseEntity<AuthenticatedUserDto> signUp(@Validated @RequestBody UserDto params) throws Exception {
		
		User user = toUser(params);
		
		UserDto userDto = userService.signUp(user);
		   
		return ResponseEntity.ok(toAuthenticatedUserDto(permissionChecker.getServiceToken(params.getEmail(), params.getPassword()), userDto));

	}

    @PostMapping("/login")
	public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params) throws Exception {
		
		UserDto userDto = userService.login(params.getEmail(), params.getPassword());
		return toAuthenticatedUserDto(permissionChecker.getServiceToken(params.getEmail(), params.getPassword()), userDto);
		
	}


	@PostMapping("/{id}/changePassword")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void secureEndpoint(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id,
													@RequestBody ChangePasswordParamsDto params) throws Exception {
        
													
		String idToken = authorizationHeader.replace("Bearer ", "");
        String userId = permissionChecker.getUserIdFromToken(idToken);
		
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		userService.changePassword(userId, params.getNewPassword());
        
    }



}
