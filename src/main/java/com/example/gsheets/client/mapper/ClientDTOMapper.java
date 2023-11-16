package com.example.gsheets.client.mapper;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;

public class ClientDTOMapper {
    public static StudentDTOClient asStudentDTO(ValueRange student) {
        return StudentDTOClient
                .builder()
                .Id(student.getValues().get(0).get(0).toString())
                .firstName(student.getValues().get(0).get(1).toString())
                .secondName(student.getValues().get(0).get(2).toString())
                .lastName(student.getValues().get(0).get(3).toString())
                .build();
    }
    public static Student asStudent (StudentDTOClient student){
        return Student
                .builder()
                .Id(student.getId())
                .firstName(student.getFirstName())
                .secondName(student.getSecondName())
                .lastName(student.getLastName())
                .build();
    }
}
