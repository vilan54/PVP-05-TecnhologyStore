package com.vilan.pablo.technology.store.model.exceptions;


public class IncorrectLoginException extends Exception {
    private String email;
    private String password;
    private String message;

    public IncorrectLoginException(String email, String password, String message){
        this.email = email;
        this.password = password;
        this.message = message;
    }

    public String getEmail(){return this.email;}

    public String getPassword(){return this.password;}

    public String getMessage(){return this.message;}
    
}
