package com.hungryBear.heroes.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperHeroController {

	@GetMapping("/hero")
	public ResponseEntity<List<String>> getHeroes() {
		List<String> heroes = Arrays.asList("Spiderman", "Batman");
		return new ResponseEntity<List<String>>(heroes, HttpStatus.OK);
	}
	
	@GetMapping("/ping/{message}")
	public String ping(@PathVariable("message") String message) {
		return message;
	}

}
