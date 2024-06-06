package com.vilan.pablo.technology.store.model.service;

import javax.management.InstanceNotFoundException;

import com.google.firebase.auth.FirebaseAuthException;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.*;

public interface UserService {
    
    String signUp(User user) throws DuplicateInstanceException, InstanceNotFoundException, FirebaseAuthException, IncorrectLoginException;

    String login(String username, String password) throws IncorrectLoginException;

}
