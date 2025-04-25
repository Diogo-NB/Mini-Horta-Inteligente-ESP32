package com.minihorta.api.minihorta_api.mappers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.minihorta.api.minihorta_api.dtos.HumiditySensorDto;
import com.minihorta.api.minihorta_api.entities.HumiditySensorEntity;

@Component
public class HumiditySensorMapper {
	public HumiditySensorEntity map(HumiditySensorDto sensorDto) {
		return HumiditySensorEntity
			.builder()
			.temperature(sensorDto.temperature())
			.humidity(sensorDto.humidity())
			.timestamp(LocalDateTime.now())
			.build();
	}
}
