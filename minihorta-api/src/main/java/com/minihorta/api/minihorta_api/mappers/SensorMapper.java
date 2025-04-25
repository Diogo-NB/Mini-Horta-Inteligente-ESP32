package com.minihorta.api.minihorta_api.mappers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.minihorta.api.minihorta_api.dtos.SensorDto;
import com.minihorta.api.minihorta_api.entities.SensorEntity;

@Component
public class SensorMapper {
	public SensorEntity map(SensorDto sensorDto) {
		return SensorEntity
			.builder()
			.temperature(sensorDto.temperature())
			.humidity(sensorDto.humidity())
			.timestamp(LocalDateTime.now())
			.build();
	}

	public List<SensorDto> map(List<SensorEntity> sensors) {
		return sensors
			.stream()
			.map(sensor -> new SensorDto(sensor.getHumidity(), sensor.getTemperature(), sensor.getTimestamp().toString()))
			.toList();
	}
}
