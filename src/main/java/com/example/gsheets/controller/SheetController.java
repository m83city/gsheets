package com.example.gsheets.controller;

import com.example.gsheets.controller.dto.StudentDTOController;
import org.springframework.web.bind.annotation.*;

@RestController

public class SheetController {
    @GetMapping("/sheet/{id}")
    public StudentDTOController getStudentById(@RequestParam String id){
        return null ;
    }
    @PutMapping("/sheet/{id}")
    public StudentDTOController updateStudentById (@RequestParam String id, @RequestBody StudentDTOController updatedStudent){
        return null;
    }
    @DeleteMapping("/sheet/{id}")
    public void deleteStudentById(@RequestParam String id){

    }
    @PostMapping("/sheet")
    public StudentDTOController createNewStudent(@RequestBody StudentDTOController newStudent){
        return null;
    }

}
