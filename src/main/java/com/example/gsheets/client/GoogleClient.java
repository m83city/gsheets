package com.example.gsheets.client;

import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.example.gsheets.client.mapper.ClientMapper.asStudent;
import static com.example.gsheets.client.mapper.ClientMapper.asStudentDTOClient;

@Repository
@RequiredArgsConstructor
public class GoogleClient {
    @Value("${spring.spread-id}")
    private String spreadId;
    private final GoogleCredentialAPI credentialAPI;

    public Student update(String id, Student student) {
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
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getById(String id) {
        List<String> targetStudent = getDataSheet()
                .stream()
                .filter(st -> st.get(0).equals(id))
                .flatMap(
                        innerList -> innerList.stream().map(Object::toString)
                )
                .toList();
        return asStudent(asStudentDTOClient(targetStudent));
    }

    public Student create(Student student) {
        try {
        List<ValueRange> newStudent = new ArrayList<>();
        newStudent.add(new ValueRange()
                .setRange("COUNTA(A)")
                .setValues(Arrays.asList(
                        Arrays.asList(
                                UUID.randomUUID().toString(),
                                student.getFirstName(),
                                student.getSecondName(),
                                student.getLastName()
                        )))
        );
            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(newStudent);
            BatchUpdateValuesResponse batchResponse = credentialAPI.initializeSheet()
                    .spreadsheets()
                    .values()
                    .batchUpdate(spreadId, batchBody)
                    .execute();
            return student;
        } catch (RuntimeException | IOException  e) {
            throw  new RuntimeException(e.getMessage(), e);
        }
    }

    public String delete(String id) {
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
        try {
            credentialAPI.initializeSheet()
                    .spreadsheets()
                    .batchUpdate(spreadId, content)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Delete successful";
    }
    private List<List<Object>> getDataSheet() {
        String spreadSheetRange = "A:D";
        List<String> ranges = Arrays.asList(spreadSheetRange);
        try {
            return credentialAPI.initializeSheet()
                    .spreadsheets()
                    .values()
                    .batchGet(spreadId)
                    .setRanges(ranges)
                    .execute()
                    .getValueRanges()
                    .get(0)
                    .getValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private OptionalInt getTargetIndex(String id) {
        List<List<Object>> a = getDataSheet();
        return IntStream.range(0, a.size())
                .filter(i -> {
                        return a.get(i).contains(id);
                })
                .findFirst();
    }
}
