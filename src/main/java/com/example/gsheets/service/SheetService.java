package com.example.gsheets.service;

import com.example.gsheets.client.GoogleClient;
import com.example.gsheets.service.domain.Student;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class SheetService {
    GoogleClient client = new GoogleClient();

    public Student getStudentById(String id) throws GeneralSecurityException, IOException {
        return client.getById(id);
    }
    public Student createStudent (Student student) throws GeneralSecurityException, IOException{
        return client.create(student);
    }
    public String updateStudent (String id, Student student) throws  GeneralSecurityException, IOException{
       client.update(id, student);
        return id;
    }
    public String deleteStudent (String id) throws  GeneralSecurityException, IOException{
       return client.delete(id);
    }
}
