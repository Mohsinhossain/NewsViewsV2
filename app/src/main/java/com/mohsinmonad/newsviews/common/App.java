package com.mohsinmonad.newsviews.common;


import android.app.Application;

import com.mohsinmonad.newsviews.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {


    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://newsapi.org/v1/";
    public  static  final String API_KEY = "6f9ceccdddae4c76905e3778f51834c1";
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return retrofit;
    }
}
