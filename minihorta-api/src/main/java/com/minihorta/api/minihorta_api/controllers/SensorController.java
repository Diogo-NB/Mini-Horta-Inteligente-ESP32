package com.minihorta.api.minihorta_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.minihorta.api.minihorta_api.dtos.SensorDto;
import com.minihorta.api.minihorta_api.services.SensorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/sensors")
public class SensorController {
	private final SensorService sensorService;

	@GetMapping("")
	public ResponseEntity<List<SensorDto>> getSensors() {
		return ResponseEntity.ok(this.sensorService.getSensors());
	}
	
	@PostMapping("")
	public ResponseEntity<Void> postSensor(@RequestBody SensorDto sensorDto) {
		if (this.sensorService.postSensor(sensorDto)) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
}
