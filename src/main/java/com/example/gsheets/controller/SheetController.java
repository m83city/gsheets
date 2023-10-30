package com.example.gsheets.controller;

import org.springframework.web.bind.annotation.*;

@RestController

public class SheetController {
    @GetMapping("/sheet/{id}")
    public StudentDTO getStudentById(@RequestParam String id){
        return ;
    }
    @PutMapping("/sheet/{id}")
    public StudentDTO updateStudentById (@RequestParam String id, @RequestBody StudentDTO updatedStudent){
        return null;
    }
    @DeleteMapping("/sheet/{id}")
    public void deleteStudentById(@RequestParam String id){

    }
    @PostMapping("/sheet")
    public StudentDTO createNewStudent(@RequestBody StudentDTO newStudent){
        return null;
    }

}
