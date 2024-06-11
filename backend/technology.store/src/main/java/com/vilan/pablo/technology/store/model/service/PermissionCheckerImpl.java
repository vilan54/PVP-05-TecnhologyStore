package com.vilan.pablo.technology.store.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vilan.pablo.technology.store.model.exceptions.IncorrectLoginException;

@Service
public class PermissionCheckerImpl implements PermissionChecker {

  @Value("${firebase.base-url}")
  private String baseUrl;

  @Value("${firebase.operation-auth}")
  private String operationAuth;

  @Value("${firebase.api-key}")
  private String firebaseKey;

  @SuppressWarnings("deprecation")
  @Override
  public String getServiceToken(String email, String password) throws FirebaseAuthException, IncorrectLoginException{
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
              throw new IncorrectLoginException(email, password, "Incorrect email or password.");
          }
          JsonParser jp = new JsonParser();
          try (InputStreamReader reader = new InputStreamReader(urlRequest.getInputStream(), "UTF-8")) {
              JsonElement root = jp.parse(reader);
              JsonObject rootobj = root.getAsJsonObject();
              token = rootobj.get("idToken").getAsString();
          }
      } catch (Exception e) {
          throw new IncorrectLoginException(email, password, e.getMessage());
      } finally {
          if (urlRequest != null) {
              urlRequest.disconnect();
          }
      }
      return token;
  }

  @Override
  public String getUserIdFromToken(String token) throws Exception {
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
      return decodedToken.getUid(); 
  }

  @Override
  public String getUserIdFromEmail(String email) throws Exception{
      try {
          UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
          return userRecord.getUid();

      } catch (FirebaseAuthException e) {
          throw new Exception("Error retrieving user by email.", e);
      }
  }
  
    
}
