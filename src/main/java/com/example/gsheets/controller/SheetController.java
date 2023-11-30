package com.example.gsheets.controller;

import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.SheetService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import static  com.example.gsheets.controller.mapper.ControllerDTOMapper.*;
import static com.example.gsheets.service.mapper.StudentMapper.fromDTOControllerToStudent;

@RestController
@RequiredArgsConstructor
public class SheetController {
   private final SheetService service;

    @GetMapping("/sheet/{id}")
    public StudentDTOController getStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        return fromStudentToDTOController(service.getStudentById(id));
    }
    @PutMapping("/sheet/{id}")
    public StudentDTOController updateStudentById (@PathVariable String id, @RequestBody StudentDTOController updatedStudent) throws GeneralSecurityException, IOException {
        service.updateStudent(id, fromDTOControllerToStudent(updatedStudent));
        return null;
    }
    @DeleteMapping("/sheet/{id}")
    public void deleteStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        service.deleteStudent(id);
    }
    @PostMapping("/sheet")
    public StudentDTOController createNewStudent(@RequestBody StudentDTOController student ) throws GeneralSecurityException, IOException {
        service.createStudent(fromDTOControllerToStudent(student));

        return null;
    }

}
