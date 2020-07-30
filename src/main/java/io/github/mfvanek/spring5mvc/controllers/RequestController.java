package io.github.mfvanek.spring5mvc.controllers;

import io.github.mfvanek.spring5mvc.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class RequestController {

    @GetMapping(path = "/v1/demo")
    public ResponseEntity<Object> log() {
        final String logMessage = "Add log entry at " + LocalDateTime.now();
        log.info(logMessage);

        return ResponseEntity.ok(logMessage);
    }

    @GetMapping(path = "/v1/demoException")
    public ResponseEntity<Object> logException() {
        try {
            throw new IllegalArgumentException("sample exception");
        } catch (IllegalArgumentException iae) {
            log.error("Log entry with exception", iae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(iae);
        }
    }

    @GetMapping(path = "/v1/welcome")
    public ResponseEntity<Task> displayWelcomeMessage() {
        Task task = new Task("Jaeger tracing demo", "Welcome from Spring MVC and Embedded Tomcat. " + LocalDateTime.now());
        log.info("Task object {}", task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
