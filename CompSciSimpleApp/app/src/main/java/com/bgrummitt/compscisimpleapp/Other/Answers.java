package com.bgrummitt.compscisimpleapp.Other;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.bgrummitt.compscisimpleapp.R;
import com.bgrummitt.compscisimpleapp.UI.AlertDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Answers {

    private Context mContext;
    public double latitude = 51.5074;
    public double longitude = 0.1278;

    private static final String TAG = Answers.class.getSimpleName();
    private String[] mForecast;

    public Answers(Context context){
        mContext = context;
        getForecast(latitude, longitude);
    }

    public String QuestionAsked(int qNumber){
        String answer = "";

        switch(qNumber){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        return answer;
    }

    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("K:m:s");
        Date dateTime = Calendar.getInstance().getTime();
        return formatter.format(dateTime);
    }

    public String getDate(){
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
         Date dateTime = Calendar.getInstance().getTime();
         return formatter.format(dateTime);
    }

    private void getForecast(double latitude, double longitude) {
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
                            setForecast(parseForecastDetails(jsonData));
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
            Toast.makeText(mContext, R.string.network_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    public void setForecast(String[] forecast){
        String[] forecastTemp = new String[2];
        forecastTemp[0] = forecast[0];
        forecastTemp[1] = forecast[1];
        mForecast = forecastTemp;
    }

    private String[] parseForecastDetails(String jsonData) throws JSONException{
        String[] forecast = new String[2];

        forecast[0] = getCurrentSummary(jsonData);
        forecast[1] = getCurrentTemperature(jsonData);

        return forecast;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        try{
            final Activity activity = (Activity) mContext;
            dialog.show(activity.getFragmentManager(), "error_dialog");
        } catch (ClassCastException e) {
            Log.d(TAG, "Can't get the fragment manager with this");
        }
    }

    public String getCurrentSummary(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);

        JSONObject currently = forecast.getJSONObject("currently");

        return currently.getString("summary");
    }

    public String getCurrentTemperature(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);

        JSONObject currently = forecast.getJSONObject("currently");

        return currently.getString("temperature");
    }

}
