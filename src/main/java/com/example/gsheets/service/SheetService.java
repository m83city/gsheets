package com.example.gsheets.service;

import com.example.gsheets.client.GoogleClient;
import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service

public class SheetService {

    private Integer getRowIndex(String id, ValueRange response) {
        List<List<Object>> values = response.getValues();
        int rowIndex = -1;
        int i = 0;
        if (values != null) {
            for (List row : values) {
                i += 1;
                if (row.get(1).equals(id)) {
                    System.out.println("There is a match! i= " + i);
                    rowIndex = i;
                }
            }
        } else {
            throw new RuntimeException("index ERORR");
        }
        return rowIndex;
    }

    public Student getStudentById(String id) throws GeneralSecurityException, IOException {
        GoogleClient client = new GoogleClient();
        //System.out.println(client.getStudentFromSheetsById(getRowIndex(id, )));

//                ValueRange response = service
//                .spreadsheets()
//                .values()
//                .get(spreadId, spreadRange)
//                .execute();


        return null;
    }
}
