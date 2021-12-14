package pt.ua.weatherforecastapp.network;

import java.util.HashMap;

import pt.ua.weatherforecastapp.datamodel.WeatherType;

public interface WeatherTypesResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}