package com.javamaster.firebase;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseConfig {
	@PostConstruct
	public void initFirebase() {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("./zala-d8638-firebase-adminsdk-47cca-8466dbda87.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			FirebaseApp.initializeApp(options);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public Firestore getFirestore() {
		return FirestoreClient.getFirestore();
	}
}

/*
 * }<!--https:// mvnrepository.com/artifact/com.google.firebase/firebase-admin
 * --> <!--https://
 * mvnrepository.com/artifact/com.google.firebase/firebase-admin -->
 * <!--https:// mvnrepository.com/artifact/com.google.firebase/firebase-admin
 * --> <!--<dependency>--><!--<groupId>com.google.firebase</groupId>--><!--<
 * artifactId>firebase-admin</artifactId>--><!--<version>9.0.0</version>--><!--<
 * scope>runtime</scope>--><!--</dependency>--><!--<dependency>--><!--<groupId>
 * com.google.firebase</groupId>--><!--<artifactId>firebase-server-sdk</
 * artifactId>--><!--<version>3.0.1</version>--><!--</dependency>--><!--<
 * dependency>--><!--<groupId>com.google.auth</groupId>--><!--<artifactId>google
 * -auth-library-oauth2-http</artifactId>--><!--<version>0.4.0</version>--><!--<
 * /dependency>-->
 */