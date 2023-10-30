package com.example.gsheets.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentEntity {
    private String Id;
    private String firstName;
    private String secondName;
    private String lastName;
}
