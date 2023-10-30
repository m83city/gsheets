package com.example.gsheets.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private String Id;
    private String firstName;
    private String secondName;
    private String lastName;
}
