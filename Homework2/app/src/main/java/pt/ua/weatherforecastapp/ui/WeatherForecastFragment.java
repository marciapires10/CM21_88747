package pt.ua.weatherforecastapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pt.ua.weatherforecastapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment {

    private static final String INFO = "";
    private String weather_info = "";

    private TextView city_name, forecast_day, precipProb, tMin, tMax, predWindDir, windSpeed, status;
    private String aForecast_day[] = new String[5];
    private String aPrecipProb[] = new String[5];
    private String aTMin[] = new String[5];
    private String aTMax[] = new String[5];
    private String aPredWindDir[] = new String[5];
    private String aWindSpeed[] = new String[5];
    private String aStatus[] = new String[5];

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    public static WeatherForecastFragment newInstance(String weather_info) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();

        Bundle args = new Bundle();
        args.putString(INFO, weather_info);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(INFO)) {
            weather_info = getArguments().getString(INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        if (weather_info != null) {
            for(int i = 0; i < 5; i++){
                aForecast_day[i] = weather_info.split("Weather:")[i+1].split("forecastDate='")[1].split("',")[0];
                aPrecipProb[i] = weather_info.split("Weather:")[i+1].split("precipitaProb=")[1].split(",")[0];
                aTMin[i] = weather_info.split("Weather:")[i+1].split("tMin=")[1].split(",")[0];
                aTMax[i] = weather_info.split("Weather:")[i+1].split("tMax=")[1].split(",")[0];
                aPredWindDir[i] = weather_info.split("Weather:")[i+1].split("predWindDir='")[1].split("',")[0];
                aWindSpeed[i] = weather_info.split("Weather:")[i+1].split("classWindSpeed=")[1].split(",")[0];
                aStatus[i] = weather_info.split("Weather:")[i+1].split("idWeatherType=")[1].split(",")[0];

            }
            Log.d("Length", aForecast_day[0]);
            Log.d("aprecip", aPrecipProb[0]);
            Log.d("tMin", aTMin[0]);
        }

        city_name = rootView.findViewById(R.id.city_name);
        forecast_day = rootView.findViewById(R.id.forecast_day);
        precipProb = rootView.findViewById(R.id.precipProb);
        tMin = rootView.findViewById(R.id.tMin);
        tMax = rootView.findViewById(R.id.tMax);
        predWindDir = rootView.findViewById(R.id.predWindDir);
        windSpeed = rootView.findViewById(R.id.windSpeed);
        status = rootView.findViewById(R.id.status);

        city_name.setText("IDK");
        forecast_day.setText(aForecast_day[0]);
        precipProb.setText(aPrecipProb[0]);
        tMin.setText(aTMin[0]);
        tMax.setText(aTMax[0]);
        predWindDir.setText(aPredWindDir[0]);
        windSpeed.setText((aWindSpeed[0]));
        status.setText(aStatus[0]);

        return rootView;
    }
}