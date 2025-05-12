package com.minihorta.api.minihorta_api.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "sensor")
public class SensorEntity {
	@Id
	@Column(name = "timestamp", unique = true)
	private LocalDateTime timestamp;
	
	@Column(name = "humidity")
	private Integer humidity;

	@Column(name = "temperature")
	private Float temperature;
}
