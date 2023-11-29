package com.example.gsheets.client.mapper;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;

public class ClientDTOMapper {
    //named index list
    static int id = 0;
    static int firstName = 1;
   static  int secondName = 2;
   static int lastName = 3;
    public static StudentDTOClient asStudentDTOClient(List<String> student) {
        return StudentDTOClient
                .builder()
                .id(student.get(id))
                .firstName(student.get(firstName))
                .secondName(student.get(secondName))
                .lastName(student.get(lastName))
                .build();
    }
}
