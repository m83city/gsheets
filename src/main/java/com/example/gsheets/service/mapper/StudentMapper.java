package com.example.gsheets.service.mapper;

import com.example.gsheets.client.dto.StudentDTOClient;
import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.domain.Student;

public class StudentMapper {
    public static Student fromDTOClientToStudent (StudentDTOClient student){
        return Student
                .builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .secondName(student.getSecondName())
                .lastName(student.getLastName())
                .build();
    }
    public static Student fromDTOControllerToStudent (StudentDTOController student){
        return Student
                .builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .secondName(student.getSecondName())
                .lastName(student.getLastName())
                .build();
    }
}
