package com.bgrummitt.stormy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "2e117feca7d4644ad25ba1a2c375d009";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forecastURL = "https://api.darksky.net/forecast/%s/%f,%f";
        forecastURL = String.format(forecastURL, apiKey, latitude, longitude);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(forecastURL)
                .build();

        Call call = client.newCall(request);

        try {
            Response callResponse = call.execute();
            if(callResponse.isSuccessful()){
                Log.v(TAG, callResponse.body().string());
            }
        }catch(IOException e){
            Log.d(TAG, "IO Exception caught", e);
        }
    }

}
