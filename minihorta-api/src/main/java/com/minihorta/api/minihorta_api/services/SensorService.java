package com.minihorta.api.minihorta_api.services;

import com.minihorta.api.minihorta_api.dtos.HumiditySensorDto;

public interface SensorService {
	Boolean postSensor(HumiditySensorDto sensorDto);
}
