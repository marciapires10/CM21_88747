package pt.ua.weatherforecastapp.network;

import java.util.HashMap;

import pt.ua.weatherforecastapp.datamodel.City;

public interface  CityResultsObserver {
    public void receiveCitiesList(HashMap<String, City> citiesCollection);
    public void onFailure(Throwable cause);
}