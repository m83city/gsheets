package com.example.gsheets.controller.mapper;

import com.example.gsheets.controller.ApiEnum;
import com.example.gsheets.controller.dto.StudentDTO;
import com.example.gsheets.service.domain.Student;

import java.util.UUID;

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
    public static Student asStudent(StudentDTO student, ApiEnum method){
        System.out.println(student);
            switch (method){
                case CREATE -> {
                        return Student
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .firstName(student.getFirstName())
                        .secondName(student.getSecondName())
                        .lastName(student.getLastName())
                        .build();}

                case UPDATE -> { return Student
                        .builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .secondName(student.getSecondName())
                        .lastName(student.getLastName())
                        .build();}
                default -> {
                    return null;
                }
            }
    }
}
