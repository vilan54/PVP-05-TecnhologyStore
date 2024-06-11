package com.vilan.pablo.technology.store.rest.dtos;

import jakarta.validation.constraints.NotNull;

public class LoginParamsDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginParamsDto() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
