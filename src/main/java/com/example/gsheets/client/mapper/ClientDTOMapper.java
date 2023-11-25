package com.example.gsheets.client.mapper;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.example.gsheets.service.domain.Student;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;

public class ClientDTOMapper {
    public static StudentDTOClient asStudentDTO(List<String> student) {
        return StudentDTOClient
                .builder()
                .Id(student.get(0))
                .firstName(student.get(1))
                .secondName(student.get(2))
                .lastName(student.get(3))
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
