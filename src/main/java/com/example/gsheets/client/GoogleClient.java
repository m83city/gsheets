package com.example.gsheets.client;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleClient {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String spreadId = "1ojNfksTD6GzD6pA_rOEFVex0C2kZqn0GdVY3ltnUFhw";
    private static final String APPLICATION_NAME = "Google Sheets API";

}
