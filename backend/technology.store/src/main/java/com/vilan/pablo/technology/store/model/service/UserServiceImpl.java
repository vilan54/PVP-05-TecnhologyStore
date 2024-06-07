package com.vilan.pablo.technology.store.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.DuplicateInstanceException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectPasswordException;

@Service
public class UserServiceImpl implements UserService{

    @Value("${firebase.base-url}")
    private String baseUrl;

    @Value("${firebase.operation-auth}")
    private String operationAuth;

    @Value("${firebase.api-key}")
    private String firebaseKey;

    @Autowired
    private PermissionChecker permissionChecker;

    @Override
    public String signUp(User user) throws DuplicateInstanceException, InstanceNotFoundException, FirebaseAuthException, IncorrectLoginException {
        if(permissionChecker.checkUserExist(user.getEmail())){
            throw new DuplicateInstanceException("project.entities.user", user.getEmail());
        }
        
        CreateRequest request = new CreateRequest()
            .setEmail(user.getEmail())
            .setPassword(user.getPassword());

        @SuppressWarnings("unused")
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        String token = login(user.getEmail(), user.getPassword());

        return token;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String login(String email, String password) throws IncorrectLoginException {
    HttpURLConnection urlRequest = null;
    String token = null;

    try {
        URL url = new URL(baseUrl + operationAuth + "?key=" + firebaseKey);
        urlRequest = (HttpURLConnection) url.openConnection();
        urlRequest.setDoOutput(true);
        urlRequest.setRequestProperty("Content-type", "application/json; charset=UTF-8");

        String jsonInputString = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"returnSecureToken\":true}";
        try (OutputStream os = urlRequest.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
            osw.write(jsonInputString);
            osw.flush();
        }

        int responseCode = urlRequest.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            StringBuilder responseMessage = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlRequest.getErrorStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    responseMessage.append(line);
                }
            }
            throw new IncorrectPasswordException();
        }

        JsonParser jp = new JsonParser();
        try (InputStreamReader reader = new InputStreamReader(urlRequest.getInputStream(), "UTF-8")) {
            JsonElement root = jp.parse(reader);
            JsonObject rootobj = root.getAsJsonObject();
            token = rootobj.get("idToken").getAsString();
        }
    } catch (Exception e){
        throw new IncorrectLoginException(email, password, e.getMessage());
    } finally {
        if (urlRequest != null) {
            urlRequest.disconnect();
        }
    }

    return token;
}
    
}
