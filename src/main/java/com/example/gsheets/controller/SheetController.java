package com.example.gsheets.controller;

import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.SheetService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import static  com.example.gsheets.controller.mapper.ControllerDTOMapper.*;
import static com.example.gsheets.service.mapper.StudentMapper.fromDTOControllerToStudent;

@RestController
public class SheetController {
    SheetService service = new SheetService();
    @GetMapping("/sheet/{id}")
    public StudentDTOController getStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        return fromStudentToDTOController(service.getStudentById(id));
    }

    @PutMapping("/sheet/{id}")
    public StudentDTOController updateStudentById (@PathVariable String id, @RequestBody StudentDTOController updatedStudent){
        return null;
    }
    @DeleteMapping("/sheet/{id}")
    public void deleteStudentById(@PathVariable String id){

    }
    @PostMapping("/sheet")
    public StudentDTOController createNewStudent(@RequestBody StudentDTOController student ) throws GeneralSecurityException, IOException {
        service.createStudent(fromDTOControllerToStudent(student));

        return null;
    }

}
