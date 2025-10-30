package com.zoyasplanet.englishapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication(exclude = {MailSenderAutoConfiguration.class})
@EnableScheduling
@Slf4j
public class EnglishAppApplication {

    public static void main(String[] args) {

        try (var lines = Files.lines(Paths.get(".env"))) {
            // Читаем .env и устанавливаем системные свойства
            lines.filter(line -> line.contains("=") && !line.startsWith("#"))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            System.setProperty(parts[0].trim(), parts[1].trim());
                        }
                    });
        } catch (IOException e) {
            log.warn("Warning: .env file not found or could not be read. Using default properties.");
            // Приложение продолжит работу с дефолтными значениями из application.properties, если они есть
        }

        // Запускаем приложение
        SpringApplication.run(EnglishAppApplication.class, args);
    }

}
