package com.example.gsheets.controller.mapper;

import com.example.gsheets.controller.dto.StudentDTO;
import com.example.gsheets.service.domain.Student;

public class ApiMapper {
        public static StudentDTO asStudentDTO(Student student){
            return StudentDTO
                    .builder()
                    .id(student.getId())
                    .firstName(student.getFirstName())
                    .secondName(student.getSecondName())
                    .lastName(student.getLastName())
                    .build();
        }
    public static Student asStudent(StudentDTO student){
        return Student
                .builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .secondName(student.getSecondName())
                .lastName(student.getLastName())
                .build();
    }
    }

