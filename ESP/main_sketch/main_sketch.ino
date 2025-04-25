#include <Arduino.h>

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

  // Stringified json dto
  String jsonBody = "{\"temperature\": " + String(temperature);
  jsonBody = jsonBody + ", \"humidity\": " + String(humidity) + " }";

  Serial.printf("[HTTP] POST /sensors body: %s\n", jsonBody);

  http.begin(HOST, PORT, "/sensors");
  http.addHeader("Content-Type", "application/json");

  int httpCode = http.POST(jsonBody);
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
    String response = http.getString();
    response.trim();
    Serial.printf("[HTTP] GET /actuator-state response: %s\n", response);

    bool state = (response == "true");
    
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
