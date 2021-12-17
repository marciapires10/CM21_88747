package pt.ua.weatherforecastapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import pt.ua.weatherforecastapp.R;
import pt.ua.weatherforecastapp.datamodel.City;
import pt.ua.weatherforecastapp.datamodel.Weather;
import pt.ua.weatherforecastapp.datamodel.WeatherType;
import pt.ua.weatherforecastapp.network.CityResultsObserver;
import pt.ua.weatherforecastapp.network.ForecastForACityResultsObserver;
import pt.ua.weatherforecastapp.network.IpmaWeatherClient;
import pt.ua.weatherforecastapp.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity implements CityListAdapter.OnNoteListener {

    private TextView tvCity;

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescription;

    private RecyclerView recyclerView;
    private CityListAdapter adapter;
    private static Context context;

    private static String weather_info = "";
    private boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String INFO = "state_of_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create recycler view
        recyclerView = (RecyclerView) findViewById(R.id.cityRecyclerView);

        // Create adapter and supply the data to be displayed
        adapter = new CityListAdapter(this, cities);
        adapter.setListener((CityListAdapter.OnNoteListener) context);

        // Connect the adapter with the recycler view
        recyclerView.setAdapter(adapter);

        // Give the recycler view a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        tvCity = findViewById(R.id.city);

        callWeatherConditionsDescriptions();

        context = this;

        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            weather_info = savedInstanceState.getString(INFO);
            if (isFragmentDisplayed) {
                //((TextView) findViewById(R.id.city_name)).append(weather_info);
            }
        }
    }

    // Inflates the menu, and adds items to the action bar if it is parent.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        context = this;
        return true;
    }

    // Handles app bar item clicks.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.menu.
        int id = item.getItemId();

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Get weather descriptions
    private void callWeatherConditionsDescriptions(){
        Log.d("MainActivity", "\nGetting weather conditions");

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescription = descriptorsCollection;
                getCitiesList();
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.d("MainActivity", "Failed to get weather conditions!");
            }
        });

    }

    // Get list of cities
    private void getCitiesList(){
        Log.d("MainActivity", "Get cities list");
        client.retrieveCitiesList(new CityResultsObserver() {
            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                //recyclerView.getAdapter().notifyItemInserted(citiesCollection.size()-1);

                adapter.setCityList(cities);
                adapter.setListener((CityListAdapter.OnNoteListener) context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.d("MainActivity", "Failed to get cities list!");
            }
        });
    }

    private void callForecastForCity(int localId){
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast){
                    weather_info += day.toString() + "\t";
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.d("MainActivity", "Failed to get forecast for 5 days");
            }
        });
    }

    @Override
    public void onNoteClick(View v, int position){
        City city;
        int pos = 0;
        int localId = 0;
        for (String key : cities.keySet()){
            if (pos == position){
                city = cities.get(key);
                Log.d("WI", weather_info);
                if (city != null){
                    localId = city.getGlobalIdLocal();
                    //callForecastForCity(localId);
                    client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
                        @Override
                        public void receiveForecastList(List<Weather> forecast) {
                            for (Weather day : forecast){
                                weather_info += day.toString() + "\t";
                            }

                            Log.d("WI", weather_info);

                            Context mcontext = v.getContext();
                            Intent intent = new Intent(mcontext, WeatherForecastActivity.class);
                            intent.putExtra(INFO, weather_info);
                            mcontext.startActivity(intent);

                        }

                        @Override
                        public void onFailure(Throwable cause) {
                            Log.d("MainActivity", "Failed to get forecast for 5 days");
                        }
                    });
                }
                else {
                    Log.d("MainActivity", "Unknown city " + city);
                }
            }
            pos++;
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putString(INFO, weather_info);
        super.onSaveInstanceState(savedInstanceState);
    }

}