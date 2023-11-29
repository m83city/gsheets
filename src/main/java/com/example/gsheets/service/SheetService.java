package com.example.gsheets.service;

import com.example.gsheets.service.domain.Student;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.example.gsheets.client.GoogleCredentialAPI.*;

import static com.example.gsheets.client.GoogleCredentialAPI.getById;


@Service
public class SheetService {

    public Student getStudentById(String id) throws GeneralSecurityException, IOException {
        return getById(id);
    }
    public Student createStudent (Student student) throws GeneralSecurityException, IOException{
        return create(student);
    }
    public String updateStudent (String id, Student student) throws  GeneralSecurityException, IOException{
        update(id, student);
        return id;
    }
    public String deleteStudent (String id) throws  GeneralSecurityException, IOException{
       return delete(id);
    }
}
