package com.example.postapp;

import android.app.Application;

import com.example.postapp.data.remote.PostApi;
import com.example.postapp.data.remote.RetrofitClient;

public class App  extends Application {

    private RetrofitClient client;
    public  static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api = client.provideApi();
    }
}
