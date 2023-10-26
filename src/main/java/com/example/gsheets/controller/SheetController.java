package com.example.gsheets.controller;

import com.example.gsheets.sheets.SheetsIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class SheetController {
    SheetsIntegration sheetsIntegration  = new SheetsIntegration();
    @GetMapping("/sheet")
    public void getValuesFromSheet () throws GeneralSecurityException, IOException {
        sheetsIntegration.sheetService();
    }
}
