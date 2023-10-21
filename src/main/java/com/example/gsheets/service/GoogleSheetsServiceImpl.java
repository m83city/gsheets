package com.example.gsheets.service;

import com.example.gsheets.api.GoogleAuthorizationConfig;
import com.google.api.client.util.Value;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class GoogleSheetsServiceImpl implements GoogleSheetsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsServiceImpl.class);
    @Value
    private String spreadsheetId;
    @Autowired
    private GoogleAuthorizationConfig googleAuthorizationConfig;
    @Override
    public void getSpreadsheetValues() throws IOException, GeneralSecurityException{
        Sheets sheetService = googleAuthorizationConfig.getSheetsService();
        Sheets.Spreadsheets.Values.BatchGet request = sheetService
                .spreadsheets()
                .values()
                .batchGet(spreadsheetId);
        request.setRanges(getSpreadSheetRange());
        request.setMajorDimension("ROWS");
        BatchGetValuesResponse response  = request.execute();
        List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
        List<Object> headers = spreadSheetValues.remove(0);
        for ( List<Object> row : spreadSheetValues ){
            LOGGER.info("{}: {}, {}: {}, {}: {}, {}: {}",
                    headers.get(0),row.get(0), headers.get(1),row.get(1),
                    headers.get(2),row.get(2), headers.get(3),row.get(3));//ddd
        }
    }
    private List<String> getSpreadSheetRange() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        Spreadsheet spreadsheet = request.execute();
        Sheet sheet = spreadsheet.getSheets().get(0);
        int row = sheet.getProperties().getGridProperties().getRowCount();
        int col = sheet.getProperties().getGridProperties().getColumnCount();
        return Collections.singletonList("R1C1:R".concat(String.valueOf(row))
                .concat("C").concat(String.valueOf(col)));
    }

}
