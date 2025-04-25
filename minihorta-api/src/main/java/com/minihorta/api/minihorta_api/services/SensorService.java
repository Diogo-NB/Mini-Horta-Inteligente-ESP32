package com.minihorta.api.minihorta_api.services;

import java.util.List;

import com.minihorta.api.minihorta_api.dtos.SensorDto;

public interface SensorService {
	Boolean postSensor(SensorDto sensorDto);
	List<SensorDto> getSensors();
}
