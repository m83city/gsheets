package com.example.gsheets.service;

import com.example.gsheets.service.domain.Student;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.example.gsheets.client.GoogleCredentialAPI.createNewStudent;

import static com.example.gsheets.client.GoogleCredentialAPI.getStudentByIdFromSheets;


@Service
public class SheetService {

    public Student getStudentById(String id) throws GeneralSecurityException, IOException {
        return getStudentByIdFromSheets(id);
    }
    public Student createStudent (Student student) throws GeneralSecurityException, IOException{
        return createNewStudent(student);
    }
}
