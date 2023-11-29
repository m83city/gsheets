package com.example.gsheets.service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
}
