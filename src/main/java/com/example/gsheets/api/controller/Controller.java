package com.example.gsheets.api.controller;

import com.example.gsheets.api.controller.dto.StudentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @GetMapping("/student/{id}")
    public StudentDTO getStudentById(@PathVariable Long id){
     return  null;
    }
    @GetMapping("/students")
    public List<StudentDTO> getAll(){
        return null;
    }
    @PostMapping("/student")
    public StudentDTO createStudent (@RequestBody StudentDTO studentDTO){

        return studentDTO;
    }
    @PutMapping("/student/{id}")
    public StudentDTO updateStudentById(@RequestBody StudentDTO studentDTO, @PathVariable Long id){

        return studentDTO;
    }
    @DeleteMapping("/student/{id}")
    public void deleteStudentById(@PathVariable Long id){

    }
}
