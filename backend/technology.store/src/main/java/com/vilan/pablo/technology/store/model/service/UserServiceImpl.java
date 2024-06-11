package com.vilan.pablo.technology.store.model.service;

import static com.vilan.pablo.technology.store.rest.dtos.UserConversor.toUserDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.vilan.pablo.technology.store.model.entities.User;
import com.vilan.pablo.technology.store.model.exceptions.DuplicateInstanceException;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;
import com.vilan.pablo.technology.store.model.exceptions.InstanceNotFoundException;
import com.vilan.pablo.technology.store.rest.common.GenericServiceImpl;
import com.vilan.pablo.technology.store.rest.dtos.UserDto;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDto> implements UserService{

    @Value("${firebase.base-url}")
    private String baseUrl;

    @Value("${firebase.operation-auth}")
    private String operationAuth;

    @Value("${firebase.api-key}")
    private String firebaseKey;

    @Autowired
    public Firestore firestore;

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl() {
        super(UserDto.class);
    }

    @Override
    public CollectionReference getCollection() {
        return firestore.collection("users");
    }

    @Override
    public UserDto signUp(User user) throws Exception {
        String password = user.getPassword();
        CreateRequest request = new CreateRequest()
            .setEmail(user.getEmail())
            .setPassword(user.getPassword());

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            save(user, userRecord.getUid());
        } catch (FirebaseAuthException e) {
            throw new DuplicateInstanceException("project.entities.user", e.getMessage());
        }
        UserDto userDto = toUserDto(user, permissionChecker.getUserIdFromEmail(user.getEmail()));
        userDto.setPassword(password);
        
        return userDto;
    }

    @SuppressWarnings("deprecation")
    @Override
    public UserDto login(String email, String password) throws Exception {
        HttpURLConnection urlRequest = null;

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
                JsonObject responseJson = JsonParser.parseString(responseMessage.toString()).getAsJsonObject();
                String errorMessage = responseJson.getAsJsonObject("error").get("message").getAsString();

                if ("INVALID_LOGIN_CREDENTIALS".equals(errorMessage)) {
                    throw new IncorrectLoginException(email, password, "Incorrect email or password.");
                } else {
                    throw new Exception("Authentication failed: " + errorMessage);
                }
            }
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlRequest.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
            String idToken = responseJson.get("idToken").getAsString();

            UserDto userDto = getByEmail(email);

            String id = permissionChecker.getUserIdFromToken(idToken);
            userDto.setId(id);

            return userDto;

        } catch (IOException e) {
            throw new Exception("Network error or problem with URL connection.", e);
        } catch (JsonParseException e) {
            throw new Exception("Error parsing JSON response.", e);
        } finally {
            if (urlRequest != null) {
                urlRequest.disconnect();
            }
        }
    }

    @Override
	public void updateProfile(String id, String name, String surname, String email) throws InstanceNotFoundException, FirebaseAuthException, InterruptedException, ExecutionException {
        UpdateRequest request = new UpdateRequest(id)
            .setEmail(email);
        FirebaseAuth.getInstance().updateUser(request);
        DocumentReference reference = getCollection().document(id);
		Map<String, Object> updates = new HashMap<>();
        updates.put("email", email);
        updates.put("name", name);
        updates.put("surname", surname);
        reference.update(updates).get();
	}

    @Override
    public void changePassword(String uid, String newPassword) throws FirebaseAuthException, InterruptedException, ExecutionException{
        String encodedPassword = passwordEncoder.encode(newPassword);
        UpdateRequest request = new UpdateRequest(uid)
            .setPassword(newPassword);
        FirebaseAuth.getInstance().updateUser(request);
 	    DocumentReference reference = getCollection().document(uid);
 	    Map<String, Object> updates = new HashMap<>();
 	    updates.put("password", encodedPassword);
 	    reference.update(updates).get();
        
    }

    
}
