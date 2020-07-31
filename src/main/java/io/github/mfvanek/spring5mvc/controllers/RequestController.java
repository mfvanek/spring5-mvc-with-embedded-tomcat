package io.github.mfvanek.spring5mvc.controllers;

import io.github.mfvanek.spring5mvc.model.Task;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class RequestController {

    private final Tracer tracer;

    @GetMapping(path = "/v1/demo")
    public ResponseEntity<Object> log() {
        final Span span = tracer.buildSpan("log").start();
        try (Scope ignored = tracer.activateSpan(span)) {
            final String logMessage = "Add log entry at " + LocalDateTime.now();
            log.info(logMessage);
            return ResponseEntity.ok(logMessage);
        } finally {
            span.finish();
        }
    }

    @GetMapping(path = "/v1/demoException")
    public ResponseEntity<Object> logException() {
        final Span span = tracer.buildSpan("logException").start();
        try (Scope ignored = tracer.activateSpan(span)) {
            try {
                throw new IllegalArgumentException("sample exception");
            } catch (IllegalArgumentException iae) {
                Tags.ERROR.set(tracer.activeSpan(), true);
                tracer.activeSpan().log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, iae));
                log.error("Log entry with exception", iae);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(iae);
            }
        } finally {
            span.finish();
        }
    }

    @GetMapping(path = "/v1/welcome")
    public ResponseEntity<Task> displayWelcomeMessage() {
        final Span span = tracer.buildSpan("displayWelcomeMessage").start();
        try (Scope ignored = tracer.activateSpan(span)) {
            final Task task = new Task("Jaeger tracing demo",
                    "Welcome from Spring MVC and Embedded Tomcat. " + LocalDateTime.now());
            log.info("Task object {}", task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } finally {
            span.finish();
        }
    }
}
