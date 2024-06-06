package com.vilan.pablo.technology.store.model.service;

import javax.management.InstanceNotFoundException;

import com.google.firebase.auth.FirebaseAuthException;

public interface PermissionChecker {
    
    boolean checkUserExist(String userId) throws InstanceNotFoundException, FirebaseAuthException;
}
