package com.example.util.jsonutil;

public class Info {
   String gas;
   String humidity;
   String temperature;
   String brightness;
   public Info(){

   }
public Info(String gas, String humidity, String temperature, String brightness) {
	super();
	this.gas = gas;
	this.humidity = humidity;
	this.temperature = temperature;
	this.brightness = brightness;
}
public String getGas() {
	return gas;
}
public void setGas(String gas) {
	this.gas = gas;
}
public String getHumidity() {
	return humidity;
}
public void setHumidity(String humidity) {
	this.humidity = humidity;
}
public String getTemperature() {
	return temperature;
}
public void setTemperature(String temperature) {
	this.temperature = temperature;
}
public String getBrightness() {
	return brightness;
}
public void setBrightness(String brightness) {
	this.brightness = brightness;
}
   
}
