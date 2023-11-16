package com.example.gsheets.controller.mapper;

import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.domain.Student;

public class ControllerDTOMapper {
        public static StudentDTOController asStudent (Student student){
            return StudentDTOController
                    .builder()
                    .Id(student.getId())
                    .firstName(student.getFirstName())
                    .secondName(student.getSecondName())
                    .lastName(student.getLastName())
                    .build();
        }
    }

