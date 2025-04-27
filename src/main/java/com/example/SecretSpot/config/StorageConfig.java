package com.example.SecretSpot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class StorageConfig {
    @Value("${spring.cloud.gcp.storage.project-id}")
    private String id;

    @Bean
    public Storage storage() throws IOException {
        ClassPathResource resource = new ClassPathResource("secretspot-455620-de9a9c775e98.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        String projectId = id;

        return StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(projectId).build().getService();
    }
}
