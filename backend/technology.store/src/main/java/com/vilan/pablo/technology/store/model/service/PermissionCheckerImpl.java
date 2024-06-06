package com.vilan.pablo.technology.store.model.service;

import javax.management.InstanceNotFoundException;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

@Service
public class PermissionCheckerImpl implements PermissionChecker {

    @Override
    public boolean checkUserExist(String email) throws InstanceNotFoundException, FirebaseAuthException{
        ListUsersPage page = FirebaseAuth.getInstance().listUsers("100");
        while (page != null) {
          for (ExportedUserRecord user : page.getValues()) {
            if(user.getEmail() == email){
                return true;
            }
          }
          page = page.getNextPage();
        }
        return false;
    }
    
}
