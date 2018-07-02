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
        Log.d(TAG, context.getPackageName());
        getForecast(latitude, longitude);
    }

    public String QuestionAsked(int qNumber, String questionTemplate){
        String answer = "";

        //Switch statement for questions
        switch(qNumber){
            case 1:
                answer = String.format(questionTemplate, getTime());
                break;
            case 2:
                answer = String.format(questionTemplate, getDate());
                break;
            case 3:
                answer = String.format(questionTemplate, mForecast[0]);
                break;
            case 4:
                answer = String.format(questionTemplate, mForecast[1]);
                break;
            case 5:
                answer = String.format("The final question will be?");
                break;
        }

        return answer;
    }

    public String getTime(){
        //Get time in format 12:23:01 Hours Minutes Seconds
        SimpleDateFormat formatter = new SimpleDateFormat("KK:mm:ss");
        Date dateTime = Calendar.getInstance().getTime();
        return formatter.format(dateTime);
    }

    public String getDate(){
         //Get date in format 05/05/14 Day Month Year
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
         Date dateTime = Calendar.getInstance().getTime();
         return formatter.format(dateTime);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "2e117feca7d4644ad25ba1a2c375d009";
        String forecastURL = "https://api.darksky.net/forecast/%s/%f,%f?lang=%s&units=%s";
        String units = "si";
        String language = "en";
        //Format all the information into the Forecast
        forecastURL = String.format(Locale.UK, forecastURL, apiKey, latitude, longitude, language, units);

        //If you can connect to the internet
        if(isNetworkAvailable()) {
            //Use OkHttp to connect to the dark sky api
            OkHttpClient client = new OkHttpClient();

            //Build new request
            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            //Make a new Request
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {}

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //Get the jsonData in string form
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        //If we got a response set the pass the data and set the forecast else create an alert
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
            //If there is an error alert the user.
            alertUserAboutError();
            Toast.makeText(mContext, R.string.network_unavailable, Toast.LENGTH_LONG).show();
            //Set the forecast that is accessed to Error messages
            String[] errorText = {"Sorry I could not connect to the internet so I am unable to answer that", "Sorry I could not connect to the internet so I am unable to answer that"};
            setForecast(errorText);
        }
    }

    public void setForecast(String[] forecast){
        mForecast = forecast;
    }

    private String[] parseForecastDetails(String jsonData) throws JSONException{
        String[] forecast = new String[2];

        //Set the first element to the summary and second element to the temperature
        forecast[0] = getCurrentSummary(jsonData);
        forecast[1] = getCurrentTemperature(jsonData);

        return forecast;
    }

    private boolean isNetworkAvailable() {
        //Get the ConnectivityManager and get the active network info. And return true of false depending on isConnected and if network info is null
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }

    private void alertUserAboutError() {
        //Create a new Dialog Fragment and display it
        AlertDialogFragment dialog = new AlertDialogFragment();
        try{
            final Activity activity = (Activity) mContext;
            dialog.show(activity.getFragmentManager(), "error_dialog");
        } catch (ClassCastException e) {
            Log.d(TAG, "Can't get the fragment manager with this");
        }
    }

    public String getCurrentSummary(String jsonData) throws JSONException{
        //Set the forecast to a JSONObject with the passed json String
        JSONObject forecast = new JSONObject(jsonData);

        //Get the Current weather Object name "currently"
        JSONObject currently = forecast.getJSONObject("currently");

        //Return the summary info from the currently object
        return currently.getString("summary");
    }

    public String getCurrentTemperature(String jsonData) throws JSONException{
        //Set the forecast to a JSONObject with the passed json String
        JSONObject forecast = new JSONObject(jsonData);

        //Get the Current weather Object name "currently"
        JSONObject currently = forecast.getJSONObject("currently");

        //Return the temperature info from the currently object
        return currently.getString("temperature");
    }

}
