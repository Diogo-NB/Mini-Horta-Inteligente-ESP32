package com.minihorta.api.minihorta_api.services.impls;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minihorta.api.minihorta_api.dtos.SensorDto;
import com.minihorta.api.minihorta_api.mappers.SensorMapper;
import com.minihorta.api.minihorta_api.repositories.SensorRepository;
import com.minihorta.api.minihorta_api.services.SensorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SensorServiceImpl implements SensorService {
	private final SensorRepository sensorRepository;
	private final SensorMapper sensorMapper;

	@Override
	public Boolean postSensor(SensorDto sensorDto) {
		var sensor = sensorMapper.map(sensorDto);

		if (this.sensorRepository.save(sensor) != null) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@Override
	public List<SensorDto> getSensors() {
		var sensors = this.sensorRepository.findAll();
		sensors.sort((sensor1, sensor2) -> sensor1.getTimestamp().compareTo(sensor2.getTimestamp()));

		return sensorMapper.map(sensors);
	}
}
