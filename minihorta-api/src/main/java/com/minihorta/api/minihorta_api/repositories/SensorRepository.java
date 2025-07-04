package com.minihorta.api.minihorta_api.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minihorta.api.minihorta_api.entities.SensorEntity;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, LocalDateTime> {
	
}
