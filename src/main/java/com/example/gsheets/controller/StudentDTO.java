package com.example.gsheets.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    private String Id;
    private String firstName;
    private String secondName;
    private String lastName;
}