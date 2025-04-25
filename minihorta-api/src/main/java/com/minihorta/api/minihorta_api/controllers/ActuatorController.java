package com.minihorta.api.minihorta_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

// @RequiredArgsConstructor
@RestController(value = "/actuators")
public class ActuatorController {
	// private final ActuatorService actuatorService;

	@GetMapping("/state")
	public Boolean getState() {
		return Boolean.FALSE;
		// return this.actuatorService.getState();
	}
}
