package com.swiftcart.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                String firebaseKeyPath = System.getenv("FIREBASE_CREDENTIALS_PATH");
                
                FirebaseOptions options;
                if (firebaseKeyPath != null && !firebaseKeyPath.isEmpty()) {
                    FileInputStream serviceAccount = new FileInputStream(firebaseKeyPath);
                    options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();
                } else {
                    // Try to use application default credentials if path not provided
                    options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.getApplicationDefault())
                            .build();
                }

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Application initialized successfully.");
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase App. Make sure GOOGLE_APPLICATION_CREDENTIALS or FIREBASE_CREDENTIALS_PATH is set correctly.");
            e.printStackTrace();
        }
    }
}
