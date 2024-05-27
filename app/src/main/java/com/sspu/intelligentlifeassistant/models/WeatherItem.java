package com.sspu.intelligentlifeassistant.models;

public class WeatherItem {
    private String cityName;
    private String weatherStatus;
    private String temperatureRange;
    private String imageResource;

    public WeatherItem(String cityName, String weatherStatus, String temperatureRange, String imageResource) {
        this.cityName = cityName;
        this.weatherStatus = weatherStatus;
        this.temperatureRange = temperatureRange;
        this.imageResource = imageResource;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
