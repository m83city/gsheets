package com.example.gsheets.controller;

import com.example.gsheets.controller.dto.StudentDTO;
import com.example.gsheets.service.SheetService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import static com.example.gsheets.controller.mapper.ApiMapper.*;

@RestController
@RequiredArgsConstructor
public class SheetController {
   private final SheetService service;

    @GetMapping("/student/{id}")
    public StudentDTO getStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        return asStudentDTO(service.getStudentById(id));
    }
    @PutMapping("/student/{id}")
    public void updateStudentById (@PathVariable String id, @RequestBody StudentDTO updatedStudent) throws GeneralSecurityException, IOException {
        service.updateStudent(id, asStudent(updatedStudent, ApiEnum.UPDATE));
    }
    @DeleteMapping("/student/{id}")
    public void deleteStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        service.deleteStudent(id);
    }
    @PostMapping("/student")
    public void createNewStudent(@RequestBody StudentDTO student ) throws GeneralSecurityException, IOException {
        service.createStudent(asStudent(student, ApiEnum.CREATE));
    }
}
