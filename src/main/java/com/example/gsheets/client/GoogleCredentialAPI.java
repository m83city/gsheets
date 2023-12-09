package com.example.gsheets.client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

@Repository
public class GoogleCredentialAPI {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    @Value("${spring.key-path}")
    private String keyPath;
    @Value("${spring.application-name}")
    private String applicationName;

    @Value("${spring.service-email}")
    private String serviceEmail;

    public Credential getCredentials(){// try catch
        try {
            GoogleCredential credential = GoogleCredential
                    .fromStream(Objects.requireNonNull(GoogleCredentialAPI.class.getResourceAsStream(keyPath)))
                    .createScoped(SCOPES)
                    .createDelegated(serviceEmail);
            return credential;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Sheets initializeSheet() {
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(applicationName)
                .build();
    }
}
