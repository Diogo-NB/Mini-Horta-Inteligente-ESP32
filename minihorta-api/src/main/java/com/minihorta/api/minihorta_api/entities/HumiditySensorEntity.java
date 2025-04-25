package com.minihorta.api.minihorta_api.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "humidity_sensor")
public class HumiditySensorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "humidity")
	private Integer humidity;

	@Column(name = "temperature")
	private Float temperature;

	@Column(name = "timestamp", unique = true)
	private LocalDateTime timestamp;
}
