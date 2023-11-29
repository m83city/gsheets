package com.example.gsheets.client;

import com.example.gsheets.service.domain.Student;
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

//    private static List<List<Object>> getDataSheet() throws IOException, GeneralSecurityException {
//        List<String> ranges = Arrays.asList("A:D");
//        return initializeSheet()
//                .spreadsheets()
//                .values()
//                .batchGet(spreadId)
//                .setRanges(ranges)
//                .execute()
//                .getValueRanges()
//                .get(0)
//                .getValues();
//    }

//    public static Student update(String id, Student student) throws IOException, GeneralSecurityException {
//        OptionalInt indexRange = IntStream.range(0, getDataSheet().size())
//                .filter(i -> {
//                    try {
//                        return getDataSheet().get(i).contains(id);
//                    } catch (IOException | GeneralSecurityException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .findFirst();
//
//        List<ValueRange> newStudent = new ArrayList<>();
//        newStudent.add(new ValueRange()
//                .setRange("A" + (1 + indexRange.getAsInt()))
//                .setValues(Arrays.asList(
//                        Arrays.asList(
//                                student.getId(),
//                                student.getFirstName(),
//                                student.getSecondName(),
//                                student.getFirstName()
//                        )))
//        );
//        try {
//            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
//                    .setValueInputOption("USER_ENTERED")
//                    .setData(newStudent);
//            BatchUpdateValuesResponse batchResponse = initializeSheet()
//                    .spreadsheets()
//                    .values()
//                    .batchUpdate(spreadId, batchBody)
//                    .execute();
//            return student;
//        } catch (RuntimeException | IOException | GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public static Student getById(String id) throws GeneralSecurityException, IOException {
//        List<String> targetStudent = getDataSheet()
//                .stream()
//                .filter(st -> st.get(0).equals(id))
//                .flatMap(
//                        innerList -> innerList.stream().map(Object::toString)
//                )
//                .toList();
//        return fromDTOClientToStudent(asStudentDTOClient(targetStudent));
//    }

//    public static Student create(Student student) throws IOException, GeneralSecurityException {
//        List<ValueRange> newStudent = new ArrayList<>();
//        newStudent.add(new ValueRange()
//                .setRange("A" + (getDataSheet().size() + 1))
//                .setValues(Arrays.asList(
//                        Arrays.asList(
//                                student.getId(),
//                                student.getFirstName(),
//                                student.getSecondName(),
//                                student.getFirstName()
//                        )))
//        );
//        try {
//            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
//                    .setValueInputOption("USER_ENTERED")
//                    .setData(newStudent);
//            BatchUpdateValuesResponse batchResponse = initializeSheet()
//                    .spreadsheets()
//                    .values()
//                    .batchUpdate(spreadId, batchBody)
//                    .execute();
//            return student;
//        } catch (RuntimeException | IOException | GeneralSecurityException e) {
//            System.out.println(e);
//        }
//        return null;
//    }

//    public static String delete(String id) throws IOException, GeneralSecurityException {
//        OptionalInt indexRange = IntStream.range(0, getDataSheet().size())
//                .filter(i -> {
//                    try {
//                        return getDataSheet().get(i).contains(id);
//                    } catch (IOException | GeneralSecurityException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .findFirst();
//
//        List<Request> requestList = new ArrayList<Request>();
//        Request request = new Request()
//                .setDeleteDimension(new DeleteDimensionRequest()
//                        .setRange(new DimensionRange()
//                                .setSheetId(0)
//                                .setDimension("ROWS")
//                                .setStartIndex(indexRange.getAsInt())
//                                .setEndIndex(indexRange.getAsInt() + 1)
//                        )
//                );
//        requestList.add(request);
//        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
//        content.setRequests(requestList);
//        initializeSheet()
//                .spreadsheets()
//                .batchUpdate(spreadId, content)
//                .execute();
//        return "Delete successful";
//    }
}
