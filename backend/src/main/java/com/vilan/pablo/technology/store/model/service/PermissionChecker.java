package com.vilan.pablo.technology.store.model.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;

public interface PermissionChecker {
    
    String getServiceToken(String email, String password) throws  FirebaseAuthException, IncorrectLoginException;

    String getUserIdFromToken(String token) throws Exception;

    String getUserIdFromEmail(String email) throws Exception;
}
