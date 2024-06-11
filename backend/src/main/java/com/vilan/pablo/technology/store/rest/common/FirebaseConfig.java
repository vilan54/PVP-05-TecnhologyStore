package com.vilan.pablo.technology.store.rest.common;

import java.io.FileInputStream;

import org.springframework.context.annotation.*;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Configuration
public class FirebaseConfig {

    @Bean
    public Firestore firestore() throws Exception{
        FileInputStream serviceAccount = new FileInputStream("./firebase-configuration.json");
        @SuppressWarnings("deprecation")
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

        return FirestoreClient.getFirestore(firebaseApp);

    }
    
}