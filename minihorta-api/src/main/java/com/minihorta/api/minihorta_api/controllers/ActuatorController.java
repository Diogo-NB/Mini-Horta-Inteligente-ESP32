package com.minihorta.api.minihorta_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// @RequiredArgsConstructor
@RestController
@RequestMapping(path = "/actuators")
public class ActuatorController {
	// private final ActuatorService actuatorService;

	@GetMapping("/state")
	public Boolean getState() {
		return Boolean.FALSE;
		// return this.actuatorService.getState();
	}
}
