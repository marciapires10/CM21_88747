package pt.ua.weatherforecastapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pt.ua.weatherforecastapp.R;

public class WeatherForecastActivity extends AppCompatActivity {

    private static String weather_info = "";

    private boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String INFO = "state_of_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        if (getIntent().getExtras() != null){
            weather_info = (String) getIntent().getExtras().get(INFO);
            Log.d("WEAT", weather_info);
        }

        /**if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            weather_info = savedInstanceState.getString(INFO);
            if (isFragmentDisplayed) {
                //((TextView) findViewById(R.id.city_name)).setText(weather_info);
            }
        }**/

        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WeatherForecastFragment fragment = WeatherForecastFragment.newInstance(weather_info);

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.weather_fragment, fragment).addToBackStack(null).commit();

        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;


    }

    /**public void onSaveInstanceState(Bundle savedInstanceState){
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putString(INFO, weather_info);
        super.onSaveInstanceState(savedInstanceState);
    }**/
}