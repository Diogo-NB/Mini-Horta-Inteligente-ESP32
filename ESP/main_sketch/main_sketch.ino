/**
 * reuseConnection.ino
 *
 *  Created on: 22.11.2015
 *
 */

#include <Arduino.h>

#include <WiFi.h>
#include <WiFiMulti.h>
#include <HTTPClient.h>

#include "DHT.h"

#define SSID "SSID"
#define PASSWORD "PASSWORD"
#define HOST "localhost"
#define PORT 3000

#define DHT_PIN 4 
#define LM393_A_PIN 36 // ANALOG INPUT 

WiFiMulti wifiMulti;

HTTPClient http;

DHT dht(DHT_PIN, DHT11);

int humidity;
float temperature;

void setup() {
  pinMode(LM393_A_PIN, INPUT);

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

void loop() {
  humidity = analogRead(LM393_A_PIN);
  temperature = dht.readTemperature();

  if (isnan(temperature)) {
    Serial.println("Failed to read temperature from DHT sensor!");
    delay(500);
    return;
  }

  if (wifiMulti.run() == WL_CONNECTED) {
    // Stringified json dto
    String jsonBody = "{\"temperature\": " + String(temperature) + "\"";
    jsonBody = jsonBody + ", \"humidity\": " + String(humidity) + "\"}";
    Serial.println("Body -> " + jsonBody);

    String uri = "/";

    http.begin(HOST, PORT, uri);
    http.addHeader("Content-Type", "application/json");

    int httpCode = http.POST(jsonBody);
    if (httpCode > 0) {
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }

  delay(2000);
}
