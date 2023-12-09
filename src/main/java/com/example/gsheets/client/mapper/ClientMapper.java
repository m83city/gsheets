package com.example.gsheets.client.mapper;

import com.example.gsheets.client.dto.StudentDTO;
import com.example.gsheets.service.domain.Student;

import java.util.List;

public class ClientMapper {
    //named index list
    static int id = 0;
    static int firstName = 1;
   static  int secondName = 2;
   static int lastName = 3;
    public static StudentDTO asStudentDTOClient(List<String> student) {
        return StudentDTO
                .builder()
                .id(student.get(id))
                .firstName(student.get(firstName))
                .secondName(student.get(secondName))
                .lastName(student.get(lastName))
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
