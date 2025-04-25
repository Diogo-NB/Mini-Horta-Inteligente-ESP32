package com.minihorta.api.minihorta_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.minihorta.api.minihorta_api.dtos.HumiditySensorDto;
import com.minihorta.api.minihorta_api.services.SensorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController(value = "/sensors")
public class SensorController {
	private final SensorService sensorService;

	@PostMapping("/humidity")
	public ResponseEntity<Void> postSensor(@RequestBody HumiditySensorDto sensorDto) {
		if (this.sensorService.postSensor(sensorDto)) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
}
