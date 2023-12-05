package com.example.gsheets.client;

import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.example.gsheets.client.mapper.ClientMapper.asStudent;
import static com.example.gsheets.client.mapper.ClientMapper.asStudentDTOClient;

@Repository
@RequiredArgsConstructor
public class GoogleClient {
    @Value("${spring.spread-id}")
    private String spreadId;
    private final GoogleCredentialAPI credentialAPI;

    private List<List<Object>> getDataSheet() throws IOException, GeneralSecurityException {
        String spreadSheetRange = "A:D";
        List<String> ranges = Arrays.asList(spreadSheetRange);
        return credentialAPI.initializeSheet()
                .spreadsheets()
                .values()
                .batchGet(spreadId)
                .setRanges(ranges)
                .execute()
                .getValueRanges()
                .get(0)
                .getValues();
    }

    private OptionalInt getTargetIndex(String id) throws GeneralSecurityException, IOException {
        return IntStream.range(0, getDataSheet().size())
                .filter(i -> {
                    try {
                        return getDataSheet().get(i).contains(id);
                    } catch (IOException | GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst();
    }

    public Student update(String id, Student student) throws IOException, GeneralSecurityException {
        List<ValueRange> newStudent = new ArrayList<>();
        newStudent.add(new ValueRange()
                .setRange("A" + (1 + getTargetIndex(id).getAsInt()))
                .setValues(Arrays.asList(
                        Arrays.asList(
                                id,
                                student.getFirstName(),
                                student.getSecondName(),
                                student.getLastName()
                        )))
        );
        try {
            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(newStudent);
            BatchUpdateValuesResponse batchResponse = credentialAPI.initializeSheet()
                    .spreadsheets()
                    .values()
                    .batchUpdate(spreadId, batchBody)
                    .execute();
            return student;
        } catch (RuntimeException | IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getById(String id) throws GeneralSecurityException, IOException {
        List<String> targetStudent = getDataSheet()
                .stream()
                .filter(st -> st.get(0).equals(id))
                .flatMap(
                        innerList -> innerList.stream().map(Object::toString)
                )
                .toList();
        return asStudent(asStudentDTOClient(targetStudent));
    }

    public Student create(Student student) throws IOException, GeneralSecurityException {
        List<ValueRange> newStudent = new ArrayList<>();
        newStudent.add(new ValueRange()
                .setRange("A" + (getDataSheet().size() + 1))
                .setValues(Arrays.asList(
                        Arrays.asList(
                                student.getId(),
                                student.getFirstName(),
                                student.getSecondName(),
                                student.getLastName()
                        )))
        );
        try {
            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(newStudent);
            BatchUpdateValuesResponse batchResponse = credentialAPI.initializeSheet()
                    .spreadsheets()
                    .values()
                    .batchUpdate(spreadId, batchBody)
                    .execute();
            return student;
        } catch (RuntimeException | IOException | GeneralSecurityException e) {
            System.out.println(e);
        }
        return null;
    }

    public String delete(String id) throws IOException, GeneralSecurityException {
        List<Request> requestList = new ArrayList<>();
        Request request = new Request()
                .setDeleteDimension(new DeleteDimensionRequest()
                        .setRange(new DimensionRange()
                                .setSheetId(0)
                                .setDimension("ROWS")
                                .setStartIndex(getTargetIndex(id).getAsInt())
                                .setEndIndex(getTargetIndex(id).getAsInt() + 1)
                        )
                );
        requestList.add(request);
        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        content.setRequests(requestList);
        credentialAPI.initializeSheet()
                .spreadsheets()
                .batchUpdate(spreadId, content)
                .execute();
        return "Delete successful";
    }
}
