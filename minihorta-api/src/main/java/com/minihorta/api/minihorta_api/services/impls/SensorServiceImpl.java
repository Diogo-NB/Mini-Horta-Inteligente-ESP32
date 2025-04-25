package com.minihorta.api.minihorta_api.services.impls;

import org.springframework.stereotype.Service;

import com.minihorta.api.minihorta_api.dtos.HumiditySensorDto;
import com.minihorta.api.minihorta_api.mappers.HumiditySensorMapper;
import com.minihorta.api.minihorta_api.repositories.HumiditySensorRepository;
import com.minihorta.api.minihorta_api.services.SensorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SensorServiceImpl implements SensorService {
	private final HumiditySensorRepository humiditySensorRepository;
	private final HumiditySensorMapper humiditySensorMapper;

	@Override
	public Boolean postSensor(HumiditySensorDto sensorDto) {
		var humiditySensor = humiditySensorMapper.map(sensorDto);

		if (this.humiditySensorRepository.save(humiditySensor) != null) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
}
