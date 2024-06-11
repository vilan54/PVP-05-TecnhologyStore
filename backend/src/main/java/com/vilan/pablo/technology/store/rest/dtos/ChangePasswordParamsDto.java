package com.vilan.pablo.technology.store.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordParamsDto {
	
	private String newPassword;
	
	public ChangePasswordParamsDto() {}

	@NotNull
	@Size(min=1, max=60)
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
