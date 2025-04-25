package com.minihorta.api.minihorta_api.services.impls;

import org.springframework.stereotype.Service;

import com.minihorta.api.minihorta_api.services.ActuatorService;

// @RequiredArgsConstructor
@Service
public class ActuatorServiceImpl implements ActuatorService {
	// private final ActuatorRepository actuatorRepository;

	@Override
	public Boolean getState() {
		
		return Boolean.TRUE;
	}
}
