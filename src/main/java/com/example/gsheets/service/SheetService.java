package com.example.gsheets.service;

import com.example.gsheets.client.GoogleCredentialAPI.*;
import com.example.gsheets.service.domain.Student;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

import static com.example.gsheets.client.mapper.ClientDTOMapper.*;

import static com.example.gsheets.client.GoogleCredentialAPI.getStudentByIdFromSheets;


@Service
public class SheetService {

    public Student getStudentById(String id) throws GeneralSecurityException, IOException {

        return asStudent(getStudentByIdFromSheets(id));
    }
}
