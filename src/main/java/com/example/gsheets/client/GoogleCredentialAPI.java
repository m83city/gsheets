package com.example.gsheets.client;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

import static com.example.gsheets.client.mapper.ClientDTOMapper.*;

public class GoogleCredentialAPI {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    final static String spreadId = "1ojNfksTD6GzD6pA_rOEFVex0C2kZqn0GdVY3ltnUFhw";
    final static String searchingStudent = "8f335c2e-9a9d-43a5-be33-4bc9a7034780";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String KEY_FILE_PATH = "/creds.json";

    public static Credential getCredentials() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential
                .fromStream(Objects.requireNonNull(GoogleCredentialAPI.class.getResourceAsStream(KEY_FILE_PATH)))
                .createScoped(SCOPES)
                .createDelegated("myname@sheetapi-405408.iam.gserviceaccount.com");

        return credential;
    }

    private static Sheets initializeSheet() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static List<List<Object>> getIdList() throws IOException, GeneralSecurityException {
        System.out.println("getIdList");
        final String spreadRange = "A2:A";

        List<List<Object>> ids = initializeSheet()
                .spreadsheets()
                .values()
                .get(spreadId, spreadRange)
                .execute()
                .getValues();
        return ids;
    }

    private static BatchGetValuesResponse getIdListBatch() throws IOException, GeneralSecurityException {//getbyId
        List<String> ranges = Arrays.asList("A:D");
        BatchGetValuesResponse readResult = initializeSheet()
                .spreadsheets()
                .values()
                .batchGet(spreadId)
                .setRanges(ranges)
                .execute();
        /*   //getValues.get(0);
         * [
         *  ["id","first","second","last"],
         *  ["8f335c2e-9a9d-43a5-be10-4bc9a703CRIS","Cris","Bord","Gerson"],
         *  ["8f335c2e-9a9d-43a5-be10-4bc9a70Carlos","Carlos","Robinson","Gers"],
         *  ["8f335c2e-9a9d-43a5-be33-4bc9a70Hover","Hover","Rob","Jeack"],
         *  ["8f335c2e-9a2d-43a5-be10-4bc9a7034Berns","Berns","Garrison","Johes"],
         *  ["8f445c2e-9a9d-43a5-be30-4bc9a703Homer","Homer","Herb","Hibert"],
         *  [],
         *  ["8f333x2e-1a2d-43a5-be99-4bc9a703Devi","Devi","Gar","John"]
         * ]
         *
         *  */
        return readResult;
    }
//
//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        getStudentByIdFromSheets("22");
//    }

    public static StudentDTOClient getStudentByIdFromSheets(String id) throws GeneralSecurityException, IOException {


        List<String> studentTarget =
                getIdListBatch()
                        .getValueRanges()
                        .get(0)
                        .getValues()
                        .stream()
                        .filter(st -> st.get(0).equals(id))
                        .flatMap(
                                innerList -> innerList.stream().map(Object::toString)
                        )
                        //  .map(objects -> Objects.toString(objects,null))
                        .toList();



      return   asStudentDTO(studentTarget);


//        for (int i = 0; i < idList.size(); i++) {
//            if (idList.get(i).get(0).equals(id)) {
//                ValueRange res = transport
//                        .spreadsheets()
//                        .values()
//                        .get(spreadId, "A" + i + ":D" + i)
//                        .execute();
//                return asStudentDTO(res);
//            }
//        }

    }
}
