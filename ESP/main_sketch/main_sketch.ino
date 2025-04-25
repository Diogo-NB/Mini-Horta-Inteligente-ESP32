#include <Arduino.h>
#include <ArduinoJson.h>

#include <WiFi.h>
#include <WiFiMulti.h>
#include <HTTPClient.h>

#include "DHT.h" // https://github.com/adafruit/DHT-sensor-library

#define SSID "SSID"
#define PASSWORD "PASSWORD"
#define HOST "localhost"
#define PORT 3000

#define DHT_PIN 4 
#define LM393_A_PIN 36 // ANALOG INPUT 
#define ACTUATOR_PIN 5

WiFiMulti wifiMulti;

HTTPClient http;

DHT dht(DHT_PIN, DHT11);

int humidity;
float temperature;

void setup() {
  pinMode(LM393_A_PIN, INPUT);
  pinMode(ACTUATOR_PIN, OUTPUT);

  Serial.begin(115200);

  Serial.println();
  Serial.println();
  Serial.println();

  dht.begin();

  for (uint8_t t = 4; t > 0; t--) {
    Serial.printf("[SETUP] WAIT %d...\n", t);
    Serial.flush();
    delay(250);
  }

  wifiMulti.addAP(SSID, PASSWORD);

  http.setReuse(true);
}

void sendSensorData() {
  if (isnan(temperature)) {
    Serial.println("Failed to read temperature from DHT sensor!");
    delay(500);
    return;
  }

  JsonDocument sensorDataDoc;
  sensorDataDoc["temperature"] = temperature;
  sensorDataDoc["humidity"] = humidity;

  Serial.printf("[HTTP] POST /sensors body:");
  serializeJsonPretty(sensorDataDoc, Serial);

  http.begin(HOST, PORT, "/sensors");
  http.addHeader("Content-Type", "application/json");

  String reqBody;
  serializeJson(sensorDataDoc, reqBody);

  int httpCode = http.POST(reqBody);
  if (httpCode > 0) {
    Serial.printf("[HTTP] POST /sensors code: %d\n", httpCode);
  } else {
    Serial.printf("[HTTP] POST /sensors error: %s\n", http.errorToString(httpCode).c_str());
  }

  http.end();
}

void updateActuatorState() {
  http.begin(HOST, PORT, "/actuator-state");
  http.addHeader("Content-Type", "application/json");

  int httpCode = http.GET();
  if (httpCode > 0) {
    Serial.printf("[HTTP] GET /actuator-state code: %d\n", httpCode);

    JsonDocument actuatorStateDoc;
    String response = http.getString();

    deserializeJson(actuatorStateDoc, response);

    Serial.printf("[HTTP] GET /actuator-state response:");
    serializeJsonPretty(actuatorStateDoc, Serial);
    
    bool state = actuatorStateDoc["state"];

    digitalWrite(ACTUATOR_PIN, state ? HIGH : LOW);
  } else {
    Serial.printf("[HTTP] GET /actuator-state error: %s\n", http.errorToString(httpCode).c_str());
  }

  http.end();
}

void readSensors() {
  humidity = analogRead(LM393_A_PIN);
  temperature = dht.readTemperature();
}

void loop() {
  readSensors();

  if (wifiMulti.run() == WL_CONNECTED) {
    sendSensorData();
  
    updateActuatorState();
  }

  delay(1000);
}
