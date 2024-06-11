package com.vilan.pablo.technology.store.model.service;

import java.util.concurrent.ExecutionException;

import com.google.firebase.auth.FirebaseAuthException;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.InstanceNotFoundException;
import com.vilan.pablo.technology.store.rest.common.GenericServiceAPI;
import com.vilan.pablo.technology.store.rest.dtos.UserDto;

public interface UserService extends GenericServiceAPI<User, UserDto>{
    
    UserDto signUp(User user) throws Exception;

    UserDto login(String username, String password) throws Exception;

    void updateProfile(String id, String name, String surname, String email) throws InstanceNotFoundException, FirebaseAuthException, InterruptedException, ExecutionException;

    void changePassword(String uid, String newPassword) throws FirebaseAuthException, InterruptedException, ExecutionException;

}
