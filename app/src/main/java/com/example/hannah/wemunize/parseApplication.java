package com.example.hannah.wemunize;

import android.app.Application;

import com.parse.Parse;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by hannah on 2/23/17.
 */

public class parseApplication extends Application {
    private static final String appid = "nhub";
    private static final String url = "https://wemanize.herokuapp.com/parse/";
    @Override
    public void onCreate() {
        super.onCreate();
        // set applicationId, and server server based on the values in the heroku settings
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(appid) //should correspond to APP_ID env variable
                .clientKey(null)// set explicitly unless client key is explicitly configured on Parse server
                .addNetworkInterceptor(new ParseLogInterceptor())
                .enableLocalDataStore()
                .server(url).build());

    }
}
