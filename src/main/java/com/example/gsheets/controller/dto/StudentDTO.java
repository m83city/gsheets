package com.example.gsheets.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
}