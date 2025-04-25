package com.minihorta.api.minihorta_api.dtos;

public record SensorDto(
	Integer humidity,
	Float temperature,
	String timestamp) {}
