package com.bgrummitt.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgrummitt.stormy.weather.Current;
import com.bgrummitt.stormy.R;
import com.bgrummitt.stormy.databinding.ActivityMainBinding;
import com.bgrummitt.stormy.weather.Day;
import com.bgrummitt.stormy.weather.Forecast;
import com.bgrummitt.stormy.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    private static final String TAG = MainActivity.class.getSimpleName();
    private Forecast mForecast;

    private ImageView iconImageView;
    final double latitude = 52.6309;
    final double longitude = 1.2974;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(latitude, longitude);

        Log.d(TAG, "Main UI Code Is Running");

    }

    private void getForecast(double latitude, double longitude) {
        final ActivityMainBinding binding =  DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        TextView darkSky = findViewById(R.id.darkSkyAttribution);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.iconImageView);

        String apiKey = "2e117feca7d4644ad25ba1a2c375d009";
        String forecastURL = "https://api.darksky.net/forecast/%s/%f,%f?lang=%s&units=%s";
        String units = "si";
        String language = "en";
        forecastURL = String.format(Locale.UK, forecastURL, apiKey, latitude, longitude, language, units);

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {}

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            Current mCurrent = mForecast.getCurrent();
                            final Current displayCurrent = new Current(
                                    mCurrent.getLocationLabel(),
                                    mCurrent.getIcon(),
                                    mCurrent.getTime(),
                                    mCurrent.getTemperature(),
                                    mCurrent.getHumidity(),
                                    mCurrent.getPrecipChance(),
                                    mCurrent.getSummary(),
                                    mCurrent.getTimeZone()
                            );
                            binding.setCurrent(displayCurrent);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayCurrent.getIconID());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.d(TAG, "IO Exception caught", e);
                    } catch (JSONException e){
                        Log.d(TAG, "JSON Exception caught", e);
                    }
                }
            });

        }else{
            alertUserAboutError();
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
        }

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for(int i = 0; i < data.length(); i++){
            JSONObject jsonHour = data.getJSONObject(i);
            hours[i] = new Hour();
            hours[i].setTimezone(timezone);
            hours[i].setSummary(jsonHour.getString("summary"));
            hours[i].setIcon(jsonHour.getString("icon"));
            hours[i].setTemp(jsonHour.getDouble("temperature"));
            hours[i].setTime(jsonHour.getLong("time"));
        }

        return hours;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i < data.length(); i++){
            JSONObject jsonDay = data.getJSONObject(i);
            days[i] = new Day();
            days[i].setTimeZone(timezone);
            days[i].setSummary(jsonDay.getString("summary"));
            days[i].setIcon(jsonDay.getString("icon"));
            days[i].setTempMax(jsonDay.getDouble("temperatureHigh"));
            days[i].setTime(jsonDay.getLong("time"));
        }

        return days;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();

        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setLocationLabel("Norwich, Norfolk");
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getLong("temperature"));
        current.setTimeZone(forecast.getString("timezone"));

        Log.d(TAG, current.getFormatedTime());

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    public void refreshOnClick(View view){
        getForecast(latitude, longitude);
        Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
    }

    public void startHourlyActivity(View view){
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
        Log.v(TAG, "Start hourly activity");
    }

    public void startDailyActivity(View view){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }

}
