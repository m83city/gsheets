package com.example.gsheets.controller.mapper;

import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.domain.Student;

public class StudentApiMapper {
        public static StudentDTOController fromStudentToDTOController (Student student){
            return StudentDTOController
                    .builder()
                    .id(student.getId())
                    .firstName(student.getFirstName())
                    .secondName(student.getSecondName())
                    .lastName(student.getLastName())
                    .build();
        }
    }

