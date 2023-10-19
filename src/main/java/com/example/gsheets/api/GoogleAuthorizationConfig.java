package com.example.gsheets.api;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAuthorizationConfig {
    @Value("{spring.application.name}")
    private String applicationName;
    @Value("{credentials.file.path}")
    private String credentialsFilePath;
    @Value("{token.directory.path}")
    private String tokenDirectoryPath;
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); //?
}
