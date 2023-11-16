package com.example.gsheets.controller;

import com.example.gsheets.controller.dto.StudentDTOController;
import com.example.gsheets.service.SheetService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import static  com.example.gsheets.controller.mapper.ControllerDTOMapper.*;

@RestController
public class SheetController {
    SheetService service = new SheetService();
    @GetMapping("/sheet/{id}")
    public StudentDTOController getStudentById(@PathVariable String id) throws GeneralSecurityException, IOException {
        System.out.println("CONTROLLER");
        return asStudent(service.getStudentById(id));
    }

    @PutMapping("/sheet/{id}")
    public StudentDTOController updateStudentById (@PathVariable String id, @RequestBody StudentDTOController updatedStudent){
        return null;
    }
    @DeleteMapping("/sheet/{id}")
    public void deleteStudentById(@PathVariable String id){

    }
    @GetMapping("/sheet/test/")
    public void testMethod () {
        String url = "https://sheets.googleapis.com/v4/spreadsheets/SPREADSHEET_ID/values/A2:D";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject(url,String.class));
       // String resourceURL = String.format(urlNameService, id);// service: name

    }
    @PostMapping("/sheet")
    public StudentDTOController createNewStudent(@PathVariable StudentDTOController newStudent){
        return null;
    }

}
