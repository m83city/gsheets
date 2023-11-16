package com.example.gsheets.client;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.gsheets.client.mapper.ClientDTOMapper.*;

public class GoogleCredentialAPI {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    final static String spreadId = "1ojNfksTD6GzD6pA_rOEFVex0C2kZqn0GdVY3ltnUFhw";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GoogleCredentialAPI.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Sheets getGoogleTransport() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static List<List<Object>> getIdList() throws IOException, GeneralSecurityException {
        final String spreadRange = "A2:A";
        return getGoogleTransport()
                .spreadsheets()
                .values()
                .get(spreadId, spreadRange)
                .execute()
                .getValues();
    }
    private static List getIdListBatch() throws IOException, GeneralSecurityException{
        List<String>  ranges = List.of("A2:D2");
        List readValue = getGoogleTransport().spreadsheets().values()
                .batchGet(spreadId)
                .setRanges(ranges)
                .execute()
                .getValueRanges()
                .get(0)
                .getValues()
                .get(0);//exact element [ [id, first, second, last], ] !!!
        return readValue;
    }

    public static StudentDTOClient getStudentByIdFromSheets(String id) throws GeneralSecurityException, IOException {
        List<List<Object>> idList = getIdList();
        System.out.println("CRED");
        System.out.println(getIdListBatch());

        Sheets transport = getGoogleTransport();


        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i).get(0).equals(id)) {
                ValueRange res = transport
                        .spreadsheets()
                        .values()
                        .get(spreadId, "A" + i + ":D" + i)
                        .execute();
                return asStudentDTO(res);
            }
        }

        return null;
    }


}
