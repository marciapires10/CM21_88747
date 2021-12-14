package pt.ua.weatherforecastapp.network;

import java.util.List;

import pt.ua.weatherforecastapp.datamodel.Weather;

public interface ForecastForACityResultsObserver {
    public void receiveForecastList(List<Weather> forecast);
    public void onFailure(Throwable cause);
}