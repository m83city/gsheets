package com.example.gsheets.client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

public class GoogleCredentialAPI {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String KEY_FILE_PATH = "/creds.json";

    public static Credential getCredentials() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential
                .fromStream(Objects.requireNonNull(GoogleCredentialAPI.class.getResourceAsStream(KEY_FILE_PATH)))
                .createScoped(SCOPES)
                .createDelegated("myname@sheetapi-405408.iam.gserviceaccount.com");

        return credential;
    }

    public static Sheets initializeSheet() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

//        /*   //getValues.get(0);
//         * [
//         *  ["id","first","second","last"],
//         *  ["8f335c2e-9a9d-43a5-be10-4bc9a703CRIS","Cris","Bord","Gerson"],
//         *  ["8f335c2e-9a9d-43a5-be10-4bc9a70Carlos","Carlos","Robinson","Gers"],
//         *  ["8f335c2e-9a9d-43a5-be33-4bc9a70Hover","Hover","Rob","Jeack"],
//         *  ["8f335c2e-9a2d-43a5-be10-4bc9a7034Berns","Berns","Garrison","Johes"],
//         *  ["8f445c2e-9a9d-43a5-be30-4bc9a703Homer","Homer","Herb","Hibert"],
//         *  [],
//         *  ["8f333x2e-1a2d-43a5-be99-4bc9a703Devi","Devi","Gar","John"]
//         * ]

}
