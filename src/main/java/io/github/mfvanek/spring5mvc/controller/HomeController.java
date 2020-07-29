package io.github.mfvanek.spring5mvc.controller;

import io.github.mfvanek.spring5mvc.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HomeController {

	@GetMapping
	public ResponseEntity<Task> displayWelcomeMessage() {
		Task task = new Task("Learn Reactive", "Reactive is an amazing paradigm.");
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
}
