package com.example.gsheets.sheets;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class SheetsIntegration {
    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "Google Sheets example";
    private static String SPREADSHEET_ID = "1ojNfksTD6GzD6pA_rOEFVex0C2kZqn0GdVY3ltnUFhw";

    private static Credential authorize() throws IOException, GeneralSecurityException {
        //  InputStream in = SheetsIntegration.class.getResourceAsStream("./credential.json");
        //  InputStream inp = GoogleAuthorizeUtil.class.getResourceAsStream("/google-sheets-client-secret.json");
        // InputStream in = SheetsIntegration.class.getResourceAsStream("./credential.json");
        //  InputStream inputStream = new FileInputStream()
        InputStream in = new FileInputStream("/Users/dmitrorostorguev/Documents/project/gsheets/src/main/java/com/example/gsheets/sheets/credential.json");
        System.out.println(in); //pass

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
        return credential;
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void sheetService() throws GeneralSecurityException, IOException {
        sheetsService = getSheetsService();
        String range = "congress!A2D2";
        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("! ! NO DATA ! !");
        } else {
            for (List row : values) {
                System.out.printf("%s %s from \n", row.get(1), row.get(2));
            }
        }

    }

}
