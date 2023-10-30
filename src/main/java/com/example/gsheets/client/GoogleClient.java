package com.example.gsheets.client;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
@Repository
public class GoogleClient {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    GoogleCredential credential = new GoogleCredential();



    private static final String APPLICATION_NAME = "Google Sheets API";

    public GoogleClient() throws GeneralSecurityException, IOException {
        credential.getCredentials(HTTP_TRANSPORT);
    }

    public StudentEntity getStudentFromSheetsById() throws IOException, GeneralSecurityException{
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return null;
    }


}
